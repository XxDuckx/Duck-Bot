package com.duckbot.utils.commands;

import com.duckbot.core.EmulatorInstance;

public class ClosePopupCommand implements ScriptCommand {
    @Override
    public void execute(EmulatorInstance inst) throws Exception {
        inst.closePopup();
    }

    @Override
    public String toString() {
        return "CLOSE_POPUP";
    }
}
