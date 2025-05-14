package com.duckbot.utils.commands;

import com.duckbot.core.EmulatorInstance;

public class WaitCommand implements ScriptCommand {
    private final long ms;

    public WaitCommand(long ms) {
        this.ms = ms;
    }

    @Override
    public void execute(EmulatorInstance inst) throws Exception {
        Thread.sleep(ms);
    }

    @Override
    public String toString() {
        return "WAIT " + ms;
    }
}