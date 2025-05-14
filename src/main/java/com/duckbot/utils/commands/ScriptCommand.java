package com.duckbot.utils.commands;

import com.duckbot.core.EmulatorInstance;

public interface ScriptCommand {
    void execute(EmulatorInstance instance) throws Exception;
}
