package com.duckbot.utils.commands;

import com.duckbot.core.EmulatorInstance;

import java.util.List;

public class RepeatCommand implements ScriptCommand {
    private final int count;
    private final List<ScriptCommand> block;

    public RepeatCommand(int count, List<ScriptCommand> block) {
        this.count = count;
        this.block = block;
    }

    @Override
    public void execute(EmulatorInstance inst) throws Exception {
        for (int i = 0; i < count; i++) {
            for (ScriptCommand cmd : block) {
                cmd.execute(inst);
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("REPEAT " + count + "\n");
        for (ScriptCommand cmd : block) {
            sb.append("    ").append(cmd.toString()).append("\n");
        }
        sb.append("END_REPEAT");
        return sb.toString();
    }
}