package com.duckbot.gui;

import com.duckbot.core.BotManager;
import com.duckbot.core.InstanceSettingsManager;
import com.duckbot.core.InstanceSettingsManager.InstanceConfig;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class InstanceRowPanel extends JPanel {
    private final InstanceConfig config;
    private final BotManager botManager;
    private final JLabel statusLabel = new JLabel("🔴 Stopped");
    private final JCheckBox selectBox = new JCheckBox();
    private final JButton startButton = new JButton("Start");
    private final JButton stopButton = new JButton("Stop");

    public InstanceRowPanel(String instanceId, BotManager botManager) {
        this.botManager = botManager;
        this.config = InstanceSettingsManager.loadConfig(instanceId);

        setLayout(new FlowLayout(FlowLayout.LEFT));
        setBackground(new Color(40, 40, 40));

        JLabel nameLabel = new JLabel("Instance: " + config.getName());
        nameLabel.setForeground(Color.WHITE);

        startButton.addActionListener(e -> {
            try {
                botManager.startInstance(config.getName());
                updateStatus();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Failed to start: " + ex.getMessage());
            }
        });

        stopButton.addActionListener(e -> {
            botManager.stopInstance(config.getName());
            updateStatus();
        });

        add(selectBox);
        add(nameLabel);
        add(statusLabel);
        add(startButton);
        add(stopButton);

        autoRefreshStatus();
    }

    private void updateStatus() {
        boolean isRunning = botManager.isInstanceRunning(config.getName());
        if (isRunning) {
            statusLabel.setText("🟢 Running");
            statusLabel.setForeground(Color.GREEN);
        } else {
            statusLabel.setText("🔴 Stopped");
            statusLabel.setForeground(Color.RED);
        }
    }

    private void autoRefreshStatus() {
        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                SwingUtilities.invokeLater(() -> updateStatus());
            }
        }, 0, 5000); // refresh every 5 seconds
    }

    public String getInstanceName() {
        return config.getName();
    }

    public boolean isSelected() {
        return selectBox.isSelected();
    }

    public void runQueueExternally() {
        // Ensure BotManager supports this method or adjust accordingly
        if (botManager != null && config != null) {
            try {
                botManager.runQueue(config.getName());
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Queue run failed: " + e.getMessage());
            }
        }
    }

    public static JPanel createControlPanel(List<InstanceRowPanel> instancePanels, BotManager botManager) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton startSelected = new JButton("Start Selected");
        JButton stopSelected = new JButton("Stop Selected");

        startSelected.addActionListener(e -> {
            for (InstanceRowPanel instancePanel : instancePanels) {
                if (instancePanel.isSelected()) {
                    try {
                        botManager.startInstance(instancePanel.getInstanceName());
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Failed to start instance: " + instancePanel.getInstanceName());
                    }
                }
            }
        });

        stopSelected.addActionListener(e -> {
            for (InstanceRowPanel instancePanel : instancePanels) {
                if (instancePanel.isSelected()) {
                    botManager.stopInstance(instancePanel.getInstanceName());
                }
            }
        });

        panel.add(startSelected);
        panel.add(stopSelected);
        return panel;
    }
}
