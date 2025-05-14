package com.duckbot.gui;

import com.duckbot.script.ScriptManager;
import javax.swing.*;

public class DuckBotLauncher {

    public static void main(String[] args) {
        // Set the look and feel to match the system's UI
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Initialize the script manager
        ScriptManager scriptManager = new ScriptManager();

        // Create and show the main window in the Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater(() -> {
            // Create the main window
            JFrame mainFrame = new JFrame("DuckBot");
            mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            // Create the EmulatorManagerPanel and pass the scriptManager
            EmulatorManagerPanel emulatorPanel = new EmulatorManagerPanel(scriptManager);

            // Add the EmulatorManagerPanel to the main window
            mainFrame.add(emulatorPanel);
            mainFrame.setSize(800, 600);
            mainFrame.setVisible(true);
        });
    }
}

