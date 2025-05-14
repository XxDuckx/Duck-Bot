package com.duckbot.gui;

import com.duckbot.script.ScriptManager;
import com.duckbot.script.ScriptManager.ScriptData;

import javax.swing.*;
import java.awt.*;

public class ScriptQueueDialog extends JDialog {

    public final ScriptManager scriptManager;
    public final EmulatorManagerPanel.Instance instance;

    // Constructor accepting Window, ScriptManager, and Instance
    public ScriptQueueDialog(Window parent, ScriptManager scriptManager, EmulatorManagerPanel.Instance instance) {
        super(parent, "Script Queue", ModalityType.APPLICATION_MODAL);
        this.scriptManager = scriptManager;
        this.instance = instance;

        setLayout(new FlowLayout());

        // Example UI components for managing script queues
        JLabel label = new JLabel("Managing scripts for: " + instance.displayName);
        add(label);

        // Example logic to access and display script details
        String scriptName = instance.scriptQueue.get(instance.currentIndex);  // Get current script
        ScriptData data = scriptManager.getScript("Game", scriptName);  // Fetch script data by category: Game
        
        // If not found by category, fall back to fetching by script name
        if (data == null) {
            data = scriptManager.getScript(scriptName);  // Fetch script by name
        }

        if (data != null) {
            JLabel scriptLabel = new JLabel("Running: " + data.getName() + " with cooldown " + data.getCooldownMs() + " ms");
            add(scriptLabel);
        }

        setSize(400, 200);
        setLocationRelativeTo(parent); // Centers the dialog on the parent window
    }
}
