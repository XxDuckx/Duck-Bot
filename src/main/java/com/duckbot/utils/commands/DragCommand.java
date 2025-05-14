package com.duckbot.utils.commands;

import com.duckbot.core.EmulatorInstance;

public class DragCommand implements ScriptCommand {
    private final int x1, y1, x2, y2;
    private final long duration;

    public DragCommand(int x1, int y1, int x2, int y2, long duration) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.duration = duration;
    }

    @Override
    public void execute(EmulatorInstance inst) throws Exception {
        inst.drag(x1, y1, x2, y2, duration);
    }

    @Override
    public String toString() {
        return "DRAG " + x1 + " " + y1 + " " + x2 + " " + y2 + " " + duration;
    }
}
