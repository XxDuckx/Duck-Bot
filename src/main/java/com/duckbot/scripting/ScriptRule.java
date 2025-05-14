package com.duckbot.scripting;

public class ScriptRule {
    private boolean enabled;

    public ScriptRule() {
        this.enabled = true;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
