package com.duckbot.utils.commands;

import com.duckbot.core.EmulatorInstance;

public class GotoCommand implements ScriptCommand {
    private final String label;

    public GotoCommand(String label) {
        this.label = label;
    }

    @Override
    public void execute(EmulatorInstance instance) {
        // GOTO is handled by the script runner, not this method
    }

    @Override
    public String toString() {
        return "GOTO " + label;
    }

    public String getLabel() {
        return label;
    }
}
