package com.duckbot.core;

import java.io.IOException;

public class AdbUtils {

    public static void launchLauncher(String deviceId) throws IOException {
        // Code to launch the emulator (ADB command example)
        ProcessBuilder builder = new ProcessBuilder("adb", "-s", deviceId, "shell", "am", "start", "-n", "com.ldplayer.app/.MainActivity");
        builder.start();
    }

    public static void reboot(String deviceId) throws IOException {
        // Reboot the emulator
        ProcessBuilder builder = new ProcessBuilder("adb", "-s", deviceId, "reboot");
        builder.start();
    }

    public static void killEmulator(String deviceId) throws IOException {
        // Kill the emulator instance
        ProcessBuilder builder = new ProcessBuilder("adb", "-s", deviceId, "emu", "kill");
        builder.start();
    }

    public static void takeScreenshot(String deviceId, String filePath) throws IOException {
        // Capture a screenshot from the emulator
        ProcessBuilder builder = new ProcessBuilder("adb", "-s", deviceId, "shell", "screencap", "-p", "/sdcard/screenshot.png");
        builder.start();
        builder = new ProcessBuilder("adb", "-s", deviceId, "pull", "/sdcard/screenshot.png", filePath);
        builder.start();
    }
}
