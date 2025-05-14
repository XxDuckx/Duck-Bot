package com.duckbot.core;

import com.duckbot.core.InstanceSettingsManager.InstanceConfig;
import com.duckbot.ldplayer.LDPlayerManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.*;

public class BotManager {
    private final Map<String, EmulatorInstance> instances = new ConcurrentHashMap<>();
    private final ExecutorService scriptExecutor = Executors.newCachedThreadPool();
    private final Set<String> runningInstances = ConcurrentHashMap.newKeySet();

    public void discoverInstances() {
        LoggingService.getInstance().log("Discovering LDPlayer instances...");
        instances.clear();
        try {
            List<String> names = LDPlayerManager.listInstances();
            for (String name : names) {
                EmulatorInstance instance = new EmulatorInstance(name);
                instances.put(name, instance);
                if (LDPlayerManager.launchInstance(name)) {
                    runningInstances.add(name);
                }
            }
            LoggingService.getInstance().log("Found and launched " + instances.size() + " LDPlayer instances");
        } catch (Exception e) {
            LoggingService.getInstance().log("Error discovering LDPlayer: " + e.getMessage());
        }
    }

    public boolean isInstanceRunning(String id) {
        return runningInstances.contains(id);
    }

    public List<String> listInstances() {
        return new ArrayList<>(instances.keySet());
    }

    public void startInstance(String id) throws Exception {
        if (instances.containsKey(id)) {
            if (runningInstances.contains(id)) {
                throw new IllegalStateException("Instance " + id + " is already running");
            }
            EmulatorInstance inst = instances.get(id);
            inst.launch();
            runningInstances.add(id);
        }
    }

    public void stopInstance(String id) {
        EmulatorInstance inst = instances.remove(id);
        if (inst != null) {
            inst.shutdown();
            runningInstances.remove(id);
        }
    }

    public void restartInstance(String id) throws Exception {
        stopInstance(id);
        startInstance(id);
    }

    public void runScript(String instanceId, com.duckbot.scripting.Script script) {
        EmulatorInstance inst = instances.get(instanceId);
        if (inst == null) {
            throw new IllegalArgumentException("Instance not found: " + instanceId);
        }
        InstanceConfig config = inst.getConfig();
        String scriptName = script.getName();
        LocalDateTime now = LocalDateTime.now();

        if (!config.shouldRunScript(scriptName, now)) {
            LoggingService.getInstance().log("Cooldown active for script '" + scriptName + "' on " + instanceId);
            return;
        }

        scriptExecutor.submit(() -> {
            try {
                LoggingService.getInstance().log("Running script '" + scriptName + "' on " + instanceId);
                script.execute(inst);
                LoggingService.getInstance().log("Script completed on: " + instanceId);
                config.recordScriptRun(scriptName, now);
                InstanceSettingsManager.saveConfig(instanceId, config);
            } catch (Exception e) {
                LoggingService.getInstance().log("Script error on " + instanceId + ": " + e.getMessage());
                e.printStackTrace();
            }
        });
    }

    public void runQueue(String instanceId) {
        EmulatorInstance inst = instances.get(instanceId);
        if (inst == null) {
            throw new IllegalArgumentException("Instance not found: " + instanceId);
        }
        scriptExecutor.submit(() -> {
            try {
                inst.runQueue();
            } catch (Exception e) {
                LoggingService.getInstance().log("Queue execution error on " + instanceId + ": " + e.getMessage());
                e.printStackTrace();
            }
        });
    }

    public List<EmulatorInstance> getAllInstances() {
        return new ArrayList<>(instances.values());
    }

    public boolean isAdbAvailable() {
        try {
            ProcessBuilder pb = new ProcessBuilder("adb", "version");
            pb.redirectErrorStream(true);
            Process proc = pb.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("Android Debug Bridge")) return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    public void shutdown() {
        scriptExecutor.shutdownNow();
        for (EmulatorInstance inst : instances.values()) {
            inst.shutdown();
        }
        instances.clear();
        runningInstances.clear();
    }
}