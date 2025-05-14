package com.duckbot.utils;

import com.duckbot.utils.commands.*;

import java.io.*;
import java.util.*;

public class ScriptLoader {
    public static Script loadByName(String game, String name) {
        File file = new File("scripts/" + game + "/" + name + ".txt");
        return loadFromFile(file);
    }

    public static Script loadFromFile(File file) {
        List<ScriptCommand> commands = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) continue;

                if (line.startsWith("IF ")) {
                    String condition = line.substring(3).trim();
                    // Look ahead to next line for actual command
                    String next = reader.readLine();
                    if (next != null) {
                        next = next.trim();
                        ScriptCommand inner = parseLine(next);
                        commands.add(new IfCommand(condition, inner));
                    }
                } else {
                    commands.add(parseLine(line));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Script(commands);
    }

    private static ScriptCommand parseLine(String line) {
        if (line.startsWith("TAP")) {
            String[] parts = line.split(" ");
            return new TapCommand(Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
        }
        if (line.startsWith("WAIT")) {
            return new WaitCommand(Integer.parseInt(line.split(" ")[1]));
        }
        if (line.startsWith("LOG")) {
            return new LogCommand(line.substring(4));
        }
        if (line.startsWith("GOTO")) {
            return new GotoCommand(line.substring(5));
        }
        if (line.startsWith("LABEL")) {
            return new LabelCommand(line.substring(6));
        }
        return new LogCommand("Unknown: " + line);
    }
}
