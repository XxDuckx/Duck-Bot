package com.duckbot.utils.commands;

import com.duckbot.core.EmulatorInstance;

public class IfCommand implements ScriptCommand {
    private final String condition;
    private final ScriptCommand innerCommand;
    private boolean lastResult = false;

    public IfCommand(String condition, ScriptCommand innerCommand) {
        this.condition = condition;
        this.innerCommand = innerCommand;
    }

    public boolean evaluateCondition(EmulatorInstance instance) {
        if (condition.startsWith("findImage(")) {
            String image = condition.substring("findImage(".length(), condition.length() - 1);
            lastResult = instance.findImage("scripts/" + instance.getGameType() + "/images/" + image);
        } else {
            lastResult = false;
        }
        return lastResult;
    }

    public boolean wasTrue() {
        return lastResult;
    }

    @Override
    public String execute(EmulatorInstance instance) {
        if (evaluateCondition(instance)) {
            if (innerCommand != null) {
                return innerCommand.execute(instance);
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "IF:" + condition;
    }
}
