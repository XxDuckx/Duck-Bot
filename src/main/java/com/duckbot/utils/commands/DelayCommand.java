package com.duckbot.utils.commands;

import com.duckbot.core.EmulatorInstance;

public class DelayCommand implements ScriptCommand {
    private final long delayMs;

    public DelayCommand(long delayMs) {
        this.delayMs = delayMs;
    }

    @Override
    public void execute(EmulatorInstance instance) throws Exception {
        Thread.sleep(delayMs);
    }

    @Override
    public String toString() {
        return "DELAY " + delayMs;
    }
}
