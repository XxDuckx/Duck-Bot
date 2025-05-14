package com.duckbot.utils.commands;

import com.duckbot.core.EmulatorInstance;

public class HomeButtonCommand implements ScriptCommand {
    @Override
    public void execute(EmulatorInstance inst) throws Exception {
        inst.pressHome();
    }

    @Override
    public String toString() {
        return "HOME_BUTTON";
    }
}
