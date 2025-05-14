package com.duckbot.utils.commands;

import com.duckbot.core.EmulatorInstance;

public class FindAndClickCommand implements ScriptCommand {
    private final String imageName;

    public FindAndClickCommand(String imageName) {
        this.imageName = imageName;
    }

    @Override
    public void execute(EmulatorInstance instance) throws Exception {
        String gameFolder = instance.getConfig().getGameType();
        String imagePath = "scripts/" + gameFolder + "/images/" + imageName;

        if (instance.findImage(imagePath)) {
            instance.tap(500, 500); // replace with actual matched coords
        } else {
            System.out.println("Image not found: " + imagePath);
        }
    }

    @Override
    public String toString() {
        return "FIND_AND_CLICK " + imageName;
    }
}
