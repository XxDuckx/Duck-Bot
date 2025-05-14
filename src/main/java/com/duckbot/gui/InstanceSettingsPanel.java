package com.duckbot.gui;

import com.duckbot.core.BotManager;
import com.duckbot.core.EmulatorInstance;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class InstanceSettingsPanel extends JPanel {
    private final BotManager botManager;

    public InstanceSettingsPanel(List<EmulatorInstance> instances, BotManager botManager) {
        this.botManager = botManager;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createTitledBorder("Instance Settings"));
        refreshWith(instances);
    }

    public void refreshWith(List<EmulatorInstance> updatedInstances) {
        this.removeAll();

        for (EmulatorInstance instance : updatedInstances) {
            InstanceRowPanel row = new InstanceRowPanel(instance.getName(), botManager);
            this.add(row);
        }

        this.revalidate();
        this.repaint();
    }
}
