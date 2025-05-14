package com.duckbot.gui;

import com.duckbot.manager.BotManager;
import com.duckbot.manager.BotAccount;
import javax.swing.*;

public class BotQueuePanel extends JPanel {

    private BotManager botManager;

    public BotQueuePanel(BotManager botManager) {
        this.botManager = botManager;
        initialize();
    }

    private void initialize() {
        // Your panel setup
        JButton addBotButton = new JButton("Add Bot");
        addBotButton.addActionListener(e -> {
            String botName = JOptionPane.showInputDialog("Enter Bot Name:");
            if (botName != null && !botName.isEmpty()) {
                botManager.addBot(botName);
            }
        });
        add(addBotButton);
    }
}
