package com.duckbot.gui;

import com.duckbot.core.BotManager;
import com.duckbot.core.EmulatorInstance;
import com.duckbot.recorder.RecorderPanel;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MainFrame extends JFrame {
    private final BotManager botManager = new BotManager();
    private final JTabbedPane tabbedPane = new JTabbedPane();
    private InstanceSettingsPanel instanceSettingsPanel; // keep reference for refresh

    public JTabbedPane getTabbedPane() {
        return tabbedPane;
    }

    public MainFrame() {
        setTitle("DuckBot Manager");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);

        // Tabs
        tabbedPane.addTab("Instances", new InstancesPanel(botManager));
        tabbedPane.addTab("Logs", new LogsPanel());
        tabbedPane.addTab("Recorder", new RecorderPanel());
        tabbedPane.addTab("Script Builder", new ScriptBuilderPanel());
        tabbedPane.addTab("Image Cropper", new ImageCropperPanel());
        tabbedPane.addTab("Script Preview", new ScriptPreviewPanel());

        // ✅ Instance Settings tab with emulator list and bot manager
        List<EmulatorInstance> emulatorList = botManager.getAllInstances();
        instanceSettingsPanel = new InstanceSettingsPanel(emulatorList, botManager);
        tabbedPane.addTab("Instance Settings", instanceSettingsPanel);

        add(tabbedPane, BorderLayout.CENTER);
    }

    // Optional: call this if bots change and you want to reload settings panel
    public void refreshInstanceSettingsPanel() {
        List<EmulatorInstance> updatedList = botManager.getAllInstances();
        instanceSettingsPanel.refreshWith(updatedList); // must implement in panel
    }
}
