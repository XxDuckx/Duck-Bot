package com.duckbot.script;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScriptManager {

	public Map<String, ScriptData> scripts = new HashMap<>();  // Store scripts by their name
	public Map<String, ScriptData> categorizedScripts = new HashMap<>(); // Store scripts by category and name

    public static class ScriptData {
    	public String name;
        public long cooldownMs;

        public ScriptData(String name, long cooldownMs) {
            this.name = name;
            this.cooldownMs = cooldownMs;
        }

        public String getName() {
            return name;
        }

        public long getCooldownMs() {
            return cooldownMs;
        }
    }

    // Method to add a script with category
    public void addScript(String category, String scriptName, ScriptData scriptData) {
        categorizedScripts.put(category + ":" + scriptName, scriptData);
    }

    // Method to add a script without category
    public void addScript(String scriptName, ScriptData scriptData) {
        scripts.put(scriptName, scriptData);
    }

    // Method to retrieve a script by name
    public ScriptData getScript(String scriptName) {
        return scripts.get(scriptName);
    }

    // Method to retrieve a script by category and script name
    public ScriptData getScript(String category, String scriptName) {
        return categorizedScripts.get(category + ":" + scriptName);
    }

    // Method to get all script names (for use in the ManageScriptsDialog)
    public List<String> getScriptNames() {
        List<String> scriptNames = new ArrayList<>();
        scriptNames.addAll(scripts.keySet());  // Adding scripts by name
        return scriptNames;
    }

    // Method to apply a script by name (for executing the script or any other application)
    public void applyScript(String scriptName) {
        ScriptData script = scripts.get(scriptName);
        if (script != null) {
            // Logic to apply the script (e.g., execute it)
            System.out.println("Applying script: " + script.getName());
        } else {
            System.out.println("Script not found: " + scriptName);
        }
    }
}

