package com.duckbot.gui;

import com.duckbot.manager.BotManager;
import com.duckbot.manager.BotInstance;
import com.duckbot.manager.BotAccount;

import javax.swing.*;
import java.util.List;

public class BotManagementPanel extends JPanel {

    private BotManager botManager;

    public BotManagementPanel(BotManager botManager) {
        this.botManager = botManager;
        initialize();
    }

    private void initialize() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        for (BotInstance botInstance : botManager.getBotInstances()) {
            JPanel botPanel = new JPanel();
            botPanel.setLayout(new BoxLayout(botPanel, BoxLayout.Y_AXIS));

            JLabel botNameLabel = new JLabel("Bot Name: " + botInstance.getBotName());
            botPanel.add(botNameLabel);

            List<BotAccount> accounts = botInstance.getAccounts();
            for (BotAccount account : accounts) {
                JCheckBox accountCheckBox = new JCheckBox(account.getEmail());
                botPanel.add(accountCheckBox);
            }

            JButton runButton = new JButton("Run Active Account");
            runButton.addActionListener(e -> botInstance.runActiveAccount());
            botPanel.add(runButton);

            add(botPanel);
        }
    }
}
