package com.duckbot.manager;

import java.util.ArrayList;
import java.util.List;

public class BotManager {

    private List<BotInstance> botInstances;

    // Constructor to initialize BotManager
    public BotManager() {
        this.botInstances = new ArrayList<>();
    }

    // Add a bot instance
    public void addBot(String botName) {
        BotInstance botInstance = new BotInstance(botName);
        botInstances.add(botInstance);
    }

    // Get all bot instances
    public List<BotInstance> getBotInstances() {
        return botInstances;
    }

    // Get bot instance by name
    public BotInstance getBotInstance(String botName) {
        for (BotInstance botInstance : botInstances) {
            if (botInstance.getBotName().equals(botName)) {
                return botInstance;
            }
        }
        return null;
    }
}
