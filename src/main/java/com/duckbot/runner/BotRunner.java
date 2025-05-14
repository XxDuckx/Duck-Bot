package com.duckbot.runner;

import com.duckbot.script.ScriptManager.ScriptData;

public class BotRunner {
    public final String deviceId;
    public final ScriptData scriptData;

    public BotRunner(String deviceId, ScriptData scriptData) {
        this.deviceId = deviceId;
        this.scriptData = scriptData;
    }

    public void run() {
        // Run the script for the specified device and script data
        System.out.println("Running script on device: " + deviceId);
        
        // For demonstration, just print script name
        System.out.println("Running script: " + scriptData.name);

        // Add the logic to execute the script here
        executeScript();
    }

    private void executeScript() {
        // Logic to execute script for the bot. This could be calling methods
        // that perform specific bot actions like clicks, inputs, etc.
        System.out.println("Executing the actions for the script.");
        
        // For now, just simulate a script execution with sleep
        try {
            Thread.sleep(5000);  // Simulate script running for 5 seconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Script execution completed.");
    }

    // This method could be used to get the last execution time (or any other tracking info)
    public static long getLastRunTime(String scriptName, String deviceId) {
        // For simplicity, just return the current time. In real usage,
        // you would track execution timestamps for each device/script pair.
        return System.currentTimeMillis();
    }
}
