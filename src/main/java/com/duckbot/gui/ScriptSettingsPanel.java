package com.duckbot.gui;

import com.duckbot.scripting.Script;
import com.duckbot.scripting.ScriptRule;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

public class ScriptSettingsPanel extends JPanel {
    private Map<String, ScriptRule> scriptRules = new HashMap<>();
    private Map<String, Script> scripts = new HashMap<>();

    public ScriptSettingsPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        JLabel label = new JLabel("Script Settings");
        add(label);

        // Add GUI controls and logic here
    }

    public void addScript(Script script) {
        scripts.put(script.getName(), script);
    }

    public void addScriptRule(String scriptName, ScriptRule rule) {
        scriptRules.put(scriptName, rule);
    }
}
