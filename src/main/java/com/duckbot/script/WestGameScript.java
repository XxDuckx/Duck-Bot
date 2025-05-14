package com.duckbot.script;

public class WestGameScript implements RunnableScript {

    // Implementing the run() method from RunnableScript interface
    @Override
    public void run() {
        // Logic for running the West Game script
        System.out.println("Running West Game Script...");
        
        // Example logic:
        healTroops();
        useSpeedUp();
    }

    private void healTroops() {
        // Your logic to heal troops in the West Game
        System.out.println("Healing troops...");
    }

    private void useSpeedUp() {
        // Your logic to use speedup in the West Game
        System.out.println("Using speedup...");
    }
}
