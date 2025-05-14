package com.duckbot.gui;

import com.duckbot.core.BotManager;
import com.duckbot.core.InstancePersistenceManager;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class InstancesPanel extends JPanel {
    private final JPanel instanceListPanel = new JPanel();
    private final Map<String, InstanceRowPanel> rowMap = new HashMap<>();
    private int instanceCounter = 1;
    private final BotManager botManager;

    public InstancesPanel(BotManager botManager) {
        this.botManager = botManager;
        setLayout(new BorderLayout());

        instanceListPanel.setLayout(new BoxLayout(instanceListPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(instanceListPanel);
        scrollPane.getViewport().setBackground(new Color(30, 30, 30));
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        add(scrollPane, BorderLayout.CENTER);

        JButton addInstance = new JButton("Create bot instance");
        JButton startSelected = new JButton("Start selected");
        JButton stopSelected = new JButton("Stop selected");
        JButton deleteSelected = new JButton("Delete selected");
        JButton runAllQueues = new JButton("Run All Queues");

        style(addInstance);
        style(startSelected);
        style(stopSelected);
        style(deleteSelected);
        style(runAllQueues);

        JPanel bottomBar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bottomBar.setBackground(new Color(25, 25, 25));
        bottomBar.add(addInstance);
        bottomBar.add(startSelected);
        bottomBar.add(stopSelected);
        bottomBar.add(deleteSelected);
        bottomBar.add(runAllQueues);
        add(bottomBar, BorderLayout.SOUTH);

        addInstance.addActionListener(e -> addNewInstance());

        startSelected.addActionListener(e -> {
            rowMap.values().stream()
                .filter(InstanceRowPanel::isSelected)
                .forEach(row -> {
                    try {
                        botManager.startInstance(row.getInstanceName());
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, "Failed to start instance: " + row.getInstanceName());
                    }
                });
        });

        stopSelected.addActionListener(e -> {
            rowMap.values().stream()
                .filter(InstanceRowPanel::isSelected)
                .forEach(row -> botManager.stopInstance(row.getInstanceName()));
        });

        deleteSelected.addActionListener(e -> deleteSelectedInstances());
        runAllQueues.addActionListener(e -> runAllInstanceQueues());

        loadPersistedInstances();
    }

    private void style(JButton button) {
        button.setBackground(new Color(60, 60, 60));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(new Color(100, 100, 100)));
    }

    private void addNewInstance() {
        String id = "instance-" + instanceCounter++;
        InstanceRowPanel row = new InstanceRowPanel(id, botManager);
        rowMap.put(id, row);
        instanceListPanel.add(row);
        saveInstanceList();
        instanceListPanel.revalidate();
        instanceListPanel.repaint();
    }

    private void deleteSelectedInstances() {
        rowMap.entrySet().removeIf(entry -> {
            if (entry.getValue().isSelected()) {
                instanceListPanel.remove(entry.getValue());
                return true;
            }
            return false;
        });
        saveInstanceList();
        instanceListPanel.revalidate();
        instanceListPanel.repaint();
    }

    private void saveInstanceList() {
        List<String> ids = rowMap.keySet().stream().sorted().collect(Collectors.toList());
        InstancePersistenceManager.saveInstanceIds(ids);
    }

    private void loadPersistedInstances() {
        List<String> saved = InstancePersistenceManager.loadInstanceIds();
        if (saved.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Welcome! Click 'Create bot instance' to add your first bot.", "No Instances Found", JOptionPane.INFORMATION_MESSAGE);
        }
        for (String id : saved) {
            InstanceRowPanel row = new InstanceRowPanel(id, botManager);
            rowMap.put(id, row);
            instanceListPanel.add(row);
            try {
                int num = Integer.parseInt(id.replace("instance-", ""));
                instanceCounter = Math.max(instanceCounter, num + 1);
            } catch (Exception ignored) {}
        }
    }

    private void runAllInstanceQueues() {
        for (InstanceRowPanel row : rowMap.values()) {
            row.runQueueExternally();
        }
    }
}
