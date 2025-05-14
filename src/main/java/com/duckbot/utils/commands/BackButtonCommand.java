package com.duckbot.utils.commands;

import com.duckbot.core.EmulatorInstance;

public class BackButtonCommand implements ScriptCommand {
    @Override
    public void execute(EmulatorInstance inst) throws Exception {
        inst.pressBack();
    }

    @Override
    public String toString() {
        return "BACK_BUTTON";
    }
}
