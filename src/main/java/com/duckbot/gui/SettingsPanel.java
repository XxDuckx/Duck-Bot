package com.duckbot.gui;

import com.duckbot.settings.SettingsManager; 
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class SettingsPanel extends JPanel {

    private JComboBox<String> themeComboBox;
    private JFrame frame;  // Store the frame reference

    public SettingsPanel(JFrame frame) {
        this.frame = frame;  // Assign the frame

        setLayout(new BorderLayout());

        // Create the settings controls panel
        JPanel settingsControls = new JPanel();
        add(settingsControls, BorderLayout.NORTH);

        JLabel themeLabel = new JLabel("Select Theme:");
        settingsControls.add(themeLabel);

        // Create the theme selection combo box
        themeComboBox = new JComboBox<>();
        List<String> savedThemes = SettingsManager.getSavedThemes();
        for (String theme : savedThemes) {
            themeComboBox.addItem(theme);
        }

        settingsControls.add(themeComboBox);

        // Action listener to apply selected theme
        themeComboBox.addActionListener(e -> {
            String selectedTheme = (String) themeComboBox.getSelectedItem();
            if (selectedTheme != null) {
                // Apply the selected theme
                SettingsManager.applyTheme(frame, selectedTheme);  // Use the passed frame
                SettingsManager.saveSelectedTheme(selectedTheme); // Save selected theme
            }
        });
    }
}
