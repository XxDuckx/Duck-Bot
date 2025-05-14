package com.duckbot.gui;

import javax.swing.*;
import java.awt.*;

public class BotSettingsPanel extends JPanel {

    public BotSettingsPanel() {
        setLayout(new BorderLayout());

        // Example components for bot settings
        JLabel label = new JLabel("Bot Settings Panel");
        label.setFont(new Font("Arial", Font.PLAIN, 24));
        add(label, BorderLayout.CENTER);
        
        // Add more controls here for your bot settings
    }
}
