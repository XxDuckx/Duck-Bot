package com.duckbot.utils.commands;

import com.duckbot.core.EmulatorInstance;

public class LongPressCommand implements ScriptCommand {
    private final int x, y;
    private final long duration;

    public LongPressCommand(int x, int y, long duration) {
        this.x = x;
        this.y = y;
        this.duration = duration;
    }

    @Override
    public void execute(EmulatorInstance inst) throws Exception {
        inst.longPress(x, y, duration);
    }

    @Override
    public String toString() {
        return "LONG_PRESS " + x + " " + y + " " + duration;
    }
}
