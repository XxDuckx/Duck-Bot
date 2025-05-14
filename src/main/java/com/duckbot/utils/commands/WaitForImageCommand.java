package com.duckbot.utils.commands;

import com.duckbot.core.EmulatorInstance;

public class WaitForImageCommand implements ScriptCommand {
    private final String imagePath;
    private final long timeout;

    public WaitForImageCommand(String imagePath, long timeout) {
        this.imagePath = imagePath;
        this.timeout = timeout;
    }

    @Override
    public void execute(EmulatorInstance instance) throws InterruptedException {
        long start = System.currentTimeMillis();
        while (System.currentTimeMillis() - start < timeout) {
            if (instance.findImage(imagePath)) {
                System.out.println("Image appeared: " + imagePath);
                return;
            }
            Thread.sleep(250);
        }
        System.out.println("Timeout waiting for: " + imagePath);
    }

    @Override
    public String toString() {
        return "WAIT_FOR_IMAGE " + imagePath + " " + timeout;
    }
}
