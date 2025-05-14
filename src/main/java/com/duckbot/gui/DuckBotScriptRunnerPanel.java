package com.duckbot.gui;

import javax.swing.*;

public class DuckBotScriptRunnerPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    public DuckBotScriptRunnerPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // Example: Add components like buttons, text fields, etc.
        JButton runScriptButton = new JButton("Run Script");
        runScriptButton.addActionListener(e -> runScript());
        add(runScriptButton);

        // Other UI elements can go here...
    }

    private void runScript() {
        // Example script execution logic
        System.out.println("Running script...");
    }
}
