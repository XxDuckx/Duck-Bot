package com.duckbot.utils.commands;

import com.duckbot.core.EmulatorInstance;

public class InputCommand implements ScriptCommand {
    private final String text;

    public InputCommand(String text) {
        this.text = text;
    }

    @Override
    public void execute(EmulatorInstance inst) throws Exception {
        inst.input(text);
    }

    @Override
    public String toString() {
        return "INPUT " + text;
    }
}
