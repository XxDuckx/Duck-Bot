package com.duckbot.utils.commands;

import com.duckbot.core.EmulatorInstance;

public class LabelCommand implements ScriptCommand {
    private final String label;

    public LabelCommand(String label) {
        this.label = label;
    }

    @Override
    public void execute(EmulatorInstance instance) {
        // Labels don't execute actions; used for GOTO matching
    }

    @Override
    public String toString() {
        return "LABEL " + label;
    }

    public String getLabel() {
        return label;
    }
}
