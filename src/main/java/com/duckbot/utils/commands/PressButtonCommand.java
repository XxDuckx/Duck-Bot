package com.duckbot.utils.commands;

import com.duckbot.core.EmulatorInstance;

public class PressButtonCommand implements ScriptCommand {
    private final String button;

    public PressButtonCommand(String button) {
        this.button = button;
    }

    @Override
    public void execute(EmulatorInstance inst) throws Exception {
        inst.pressButton(button);
    }

    @Override
    public String toString() {
        return "PRESS_BUTTON " + button;
    }
}