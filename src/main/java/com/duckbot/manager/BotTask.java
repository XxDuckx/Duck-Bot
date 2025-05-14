package com.duckbot.manager;

public class BotTask {
    public String scriptName;
    public String deviceId;
    public int delayBefore;
    public int delayAfter;

    public BotTask(String scriptName, String deviceId, int delayBefore, int delayAfter) {
        this.scriptName = scriptName;
        this.deviceId   = deviceId;
        this.delayBefore= delayBefore;
        this.delayAfter = delayAfter;
    }

    @Override
    public String toString() {
        return scriptName + " [Device: " + deviceId + ", Delay: " + delayBefore + "/" + delayAfter + "]";
    }
}
