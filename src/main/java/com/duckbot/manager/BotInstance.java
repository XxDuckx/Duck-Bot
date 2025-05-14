package com.duckbot.manager;

import java.util.ArrayList;
import java.util.List;

public class BotInstance {
    private String botName; // Name of the bot
    private List<BotAccount> accounts; // List of accounts for the bot
    private BotAccount activeAccount; // Active account for the bot

    // Constructor to initialize BotInstance with name and ScriptManager
    public BotInstance(String botName) {
        this.botName = botName;
        this.accounts = new ArrayList<>();
        this.activeAccount = null;
    }

    // Getter for the bot name
    public String getBotName() {
        return botName;
    }

    // Setter for the bot name
    public void setBotName(String botName) {
        this.botName = botName;
    }

    // Getter for accounts
    public List<BotAccount> getAccounts() {
        return accounts;
    }

    // Add account to the bot instance
    public void addAccount(BotAccount account) {
        this.accounts.add(account);
    }

    // Set active account
    public void setActiveAccount(BotAccount account) {
        this.activeAccount = account;
    }

    // Get active account
    public BotAccount getActiveAccount() {
        return activeAccount;
    }

    // Run the active account (this would trigger whatever logic you need for the active account)
    public void runActiveAccount() {
        if (activeAccount != null) {
            // Your logic for running the active account goes here
            System.out.println("Running account: " + activeAccount.getEmail());
        }
    }

    // Check if account exists by email
    public boolean containsAccount(String email) {
        for (BotAccount account : accounts) {
            if (account.getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }

    // Get display name for the bot instance
    public String getDisplayName() {
        return botName;
    }

    // Override toString for easy printing
    @Override
    public String toString() {
        return "BotInstance{" +
                "botName='" + botName + '\'' +
                ", accounts=" + accounts +
                '}';
    }
}
