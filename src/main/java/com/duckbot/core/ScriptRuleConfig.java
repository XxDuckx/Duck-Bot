package com.duckbot.core;

import java.util.List;

public class ScriptRuleConfig {
    public boolean enabled = false;
    public List<String> days;
    public String startTime;
    public String endTime;
    public int cooldownMinutes;

    public ScriptRuleConfig() {}
}
