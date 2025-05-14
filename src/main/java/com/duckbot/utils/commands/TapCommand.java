package com.duckbot.utils.commands;

import com.duckbot.core.EmulatorInstance;

public class TapCommand implements ScriptCommand {
    private final int x;
    private final int y;

    public TapCommand(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void execute(EmulatorInstance inst) throws Exception {
        inst.tap(x, y);
    }

    @Override
    public String toString() {
        return "TAP " + x + " " + y;
    }
}