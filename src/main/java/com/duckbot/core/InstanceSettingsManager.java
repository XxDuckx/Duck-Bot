package com.duckbot.core;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.*;

public class InstanceSettingsManager {
    private static final Map<String, InstanceConfig> configs = new HashMap<>();
    private static final Path CONFIG_DIR = Paths.get("configs");

    static {
        try {
            if (!Files.exists(CONFIG_DIR)) {
                Files.createDirectories(CONFIG_DIR);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static InstanceConfig loadConfig(String name) {
        InstanceConfig fromFile = loadConfigFromFile(name);
        configs.put(name, fromFile);
        return fromFile;
    }

    public static void saveConfig(String name, InstanceConfig config) {
        configs.put(name, config);
        saveConfigToFile(name, config);
    }

    private static void saveConfigToFile(String name, InstanceConfig config) {
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            Path path = CONFIG_DIR.resolve(name + ".json");
            try (FileWriter writer = new FileWriter(path.toFile())) {
                gson.toJson(config, writer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static InstanceConfig loadConfigFromFile(String name) {
        try {
            Path path = CONFIG_DIR.resolve(name + ".json");
            if (Files.exists(path)) {
                Gson gson = new GsonBuilder().create();
                try (FileReader reader = new FileReader(path.toFile())) {
                    return gson.fromJson(reader, InstanceConfig.class);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new InstanceConfig(name);
    }

    public static class InstanceConfig {
        private String name;
        private String gameType;
        private String assignedScript;
        private boolean enabled = true;
        private Map<String, LocalDateTime> lastRunTimes = new HashMap<>();
        private List<String> loadouts = new ArrayList<>(Arrays.asList("Default", "Farm", "War"));

        public InstanceConfig(String name) {
            this.name = name;
        }

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

        public String getGameType() { return gameType; }
        public void setGameType(String gameType) { this.gameType = gameType; }

        public String getAssignedScript() { return assignedScript; }
        public void setAssignedScript(String assignedScript) { this.assignedScript = assignedScript; }

        public boolean isEnabled() { return enabled; }
        public void setEnabled(boolean enabled) { this.enabled = enabled; }

        public void recordScriptRun(String scriptName, LocalDateTime time) {
            lastRunTimes.put(scriptName, time);
        }

        public boolean shouldRunScript(String scriptName, LocalDateTime currentTime) {
            LocalDateTime lastRun = lastRunTimes.get(scriptName);
            return lastRun == null || currentTime.isAfter(lastRun.plusMinutes(5));
        }

        public List<String> getLoadoutNames() {
            return loadouts;
        }

        public void setLoadoutNames(List<String> loadouts) {
            this.loadouts = loadouts;
        }
    }
}
