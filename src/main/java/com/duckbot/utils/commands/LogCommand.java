package com.duckbot.utils.commands;

import com.duckbot.core.EmulatorInstance;

public class LogCommand implements ScriptCommand {
    private final String message;

    public LogCommand(String message) {
        this.message = message;
    }

    @Override
    public void execute(EmulatorInstance instance) {
        System.out.println("[SCRIPT LOG] " + message);
    }

    @Override
    public String toString() {
        return "LOG " + message;
    }
}
