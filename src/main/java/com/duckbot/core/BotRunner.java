package com.duckbot.core;

import com.duckbot.script.ScriptManager.ScriptData;

public class BotRunner {

    private String deviceId;
    private ScriptData data;

    public BotRunner(String deviceId, ScriptData data) {
        this.deviceId = deviceId;
        this.data = data;
    }

    public void run() {
        System.out.println("Running script on device: " + deviceId);
    }

    public static long getLastRunTime(String scriptName, String deviceId) {
        return 0; // Placeholder, should return last run time from logs or database
    }
}
