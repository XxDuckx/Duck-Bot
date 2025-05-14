package com.duckbot.gui;

import com.duckbot.core.InstanceSettingsManager;
import com.duckbot.core.InstanceSettingsManager.InstanceConfig;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.stream.Collectors;

public class ConfigEditorPanel extends JPanel {
    public ConfigEditorPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createTitledBorder("Instance Config Editor"));

        try {
            List<String> configFiles = Files.list(Paths.get("configs"))
                .filter(f -> f.toString().endsWith(".json"))
                .map(f -> f.getFileName().toString().replace(".json", ""))
                .collect(Collectors.toList());

            for (String name : configFiles) {
                add(new ConfigRowPanel(InstanceSettingsManager.loadConfig(name)));
            }
        } catch (IOException e) {
            add(new JLabel("Error reading config folder: " + e.getMessage()));
        }
    }

    static class ConfigRowPanel extends JPanel {
        private final InstanceConfig config;

        public ConfigRowPanel(InstanceConfig config) {
            this.config = config;
            setLayout(new FlowLayout(FlowLayout.LEFT));

            JTextField nameField = new JTextField(config.getName(), 10);
            JCheckBox enabled = new JCheckBox("Enabled", config.isEnabled());
            JTextField gameType = new JTextField(config.getGameType(), 8);
            JTextField script = new JTextField(config.getAssignedScript(), 8);
            JTextField loadouts = new JTextField(String.join(",", config.getLoadoutNames()), 15);

            JButton save = new JButton("Save");
            save.addActionListener(e -> {
                config.setName(nameField.getText());
                config.setEnabled(enabled.isSelected());
                config.setGameType(gameType.getText());
                config.setAssignedScript(script.getText());
                config.setLoadoutNames(List.of(loadouts.getText().split(",")));
                InstanceSettingsManager.saveConfig(config.getName(), config);
                JOptionPane.showMessageDialog(this, "Saved: " + config.getName());
            });

            add(new JLabel("Name:"));
            add(nameField);
            add(enabled);
            add(new JLabel("Game:"));
            add(gameType);
            add(new JLabel("Script:"));
            add(script);
            add(new JLabel("Loadouts:"));
            add(loadouts);
            add(save);
        }
    }
}
