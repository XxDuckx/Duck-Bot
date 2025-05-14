package com.duckbot.utils.commands;

import com.duckbot.core.EmulatorInstance;

public class ScreenshotCommand implements ScriptCommand {
    private final String path;

    public ScreenshotCommand(String path) {
        this.path = path;
    }

    @Override
    public void execute(EmulatorInstance inst) throws Exception {
        inst.takeScreenshot(path);
    }

    @Override
    public String toString() {
        return "SCREENSHOT " + path;
    }
}