package com.duckbot.scripting;

import com.duckbot.core.EmulatorInstance;
import com.duckbot.utils.commands.ScriptCommand;

import java.util.List;

public class Script {
    private final String name;
    private final List<ScriptCommand> commands;

    // ✅ Required constructor for ScriptLoader
    public Script(String name, List<ScriptCommand> commands) {
        this.name = name;
        this.commands = commands;
    }

    public String getName() {
        return name;
    }

    public List<ScriptCommand> getCommands() {
        return commands;
    }

    public void execute(EmulatorInstance instance) throws Exception {
        for (ScriptCommand cmd : commands) {
            cmd.execute(instance);
        }
    }
}
