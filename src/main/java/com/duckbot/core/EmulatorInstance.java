package com.duckbot.core;

import com.duckbot.core.InstanceSettingsManager.InstanceConfig;
import com.duckbot.ldplayer.LDPlayerManager;
import com.duckbot.scripting.Script;
import com.duckbot.utils.ScriptLoader;
import com.duckbot.utils.commands.ScriptCommand;
import com.duckbot.utils.commands.IfCommand;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class EmulatorInstance {
    private final String name;
    private boolean running;
    private final InstanceConfig config;

    public EmulatorInstance(String name) {
        this.name = name;
        this.running = false;
        this.config = InstanceSettingsManager.loadConfig(name);
    }

    public String getName() { return name; }
    public String getId() { return name; }
    public boolean isRunning() { return running; }
    public void setRunning(boolean running) { this.running = running; }

    public String getGameType() { return config.getGameType(); }
    public InstanceConfig getConfig() { return config; }

    public List<String> getLoadoutNames() {
        List<String> loadouts = new ArrayList<>();
        loadouts.add("Default");
        loadouts.add("Farm");
        return loadouts;
    }

    public void launch() {
        log("Launching emulator");
        LDPlayerManager.launchInstance(name);
    }

    public void shutdown() {
        log("Shutting down emulator");
        LDPlayerManager.quitInstance(name);
    }

    public void pressBack() { log("Pressing back"); }
    public void pressHome() { log("Pressing home"); }
    public void closePopup() { log("Closing popup"); }
    public void drag(int x1, int y1, int x2, int y2, long duration) {}
    public void swipe(int x1, int y1, int x2, int y2, long duration) {}
    public void longPress(int x, int y, long duration) {}
    public void tap(int x, int y) {}
    public void input(String text) {}
    public void pressButton(String button) {}
    public void takeScreenshot(String filename) {}
    public boolean findImage(String imagePath) { return false; }

    public void runQueue() {
        log("Running assigned script queue...");
        String game = config.getGameType();
        String scriptName = config.getAssignedScript();

        if (scriptName == null || scriptName.isEmpty()) {
            log("No script assigned.");
            return;
        }

        try {
            Script script = ScriptLoader.loadByName(game, scriptName);
            if (script == null) {
                log("Failed to load script: " + scriptName);
                return;
            }
            List<ScriptCommand> commands = script.getCommands();
            int pointer = 0;
            boolean skipBlock = false;

            while (pointer < commands.size()) {
                ScriptCommand cmd = commands.get(pointer);
                String cmdText = cmd.toString();

                if (cmd instanceof IfCommand) {
                    IfCommand ifCmd = (IfCommand) cmd;
                    skipBlock = !ifCmd.evaluateCondition(this);
                    pointer++;
                    continue;
                }

                if (cmdText.equalsIgnoreCase("ELSE")) {
                    skipBlock = !skipBlock;
                    pointer++;
                    continue;
                }

                if (cmdText.equalsIgnoreCase("ENDIF")) {
                    skipBlock = false;
                    pointer++;
                    continue;
                }

                if (!skipBlock) {
                    String result = cmd.execute(this);
                    if (result != null && result.startsWith("GOTO:")) {
                        String label = result.substring(5);
                        int newIndex = findLabel(commands, label);
                        if (newIndex >= 0) {
                            pointer = newIndex;
                            continue;
                        } else {
                            log("Label not found: " + label);
                        }
                    }
                }

                pointer++;
            }

            config.recordScriptRun(scriptName, LocalDateTime.now());
            InstanceSettingsManager.saveConfig(name, config);
        } catch (Exception e) {
            log("Error running script queue: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private int findLabel(List<ScriptCommand> commands, String label) {
        for (int i = 0; i < commands.size(); i++) {
            ScriptCommand cmd = commands.get(i);
            if (cmd.toString().equalsIgnoreCase("LABEL:" + label)) {
                return i;
            }
        }
        return -1;
    }

    private void log(String message) {
        LoggingService.getInstance().log("[" + name + "] " + message);
    }
}