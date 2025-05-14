package com.duckbot.utils;

import com.duckbot.scripting.Script;
import com.duckbot.utils.commands.ScriptCommand;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ScriptBuilder {
    private final List<Script> scripts = new ArrayList<>();

    public void addScript(Script script) {
        scripts.add(script);
    }

    public List<Script> getScripts() {
        return scripts;
    }

    public void clearScripts() {
        scripts.clear();
    }

    public void exportScriptsToFolder(File folder) throws IOException {
        if (!folder.exists()) folder.mkdirs();

        for (Script script : scripts) {
            File outFile = new File(folder, script.getName() + ".txt");
            try (FileWriter writer = new FileWriter(outFile)) {
                for (ScriptCommand cmd : script.getCommands()) {
                    writer.write(cmd.toString() + System.lineSeparator());
                }
            }
        }
    }

    public boolean hasScripts() {
        return !scripts.isEmpty();
    }
}
