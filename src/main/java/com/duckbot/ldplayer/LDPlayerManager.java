package com.duckbot.ldplayer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class LDPlayerManager {
    private static final String LD_CONSOLE = "C:\\LDPlayer\\LDPlayer4\\ldconsole.exe";

    public static List<String> listInstances() {
        List<String> instances = new ArrayList<>();
        try {
            ProcessBuilder pb = new ProcessBuilder(LD_CONSOLE, "list2");
            Process process = pb.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length > 1) {
                    instances.add(parts[1].trim()); // instance name
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return instances;
    }

    public static boolean launchInstance(String name) {
        try {
            ProcessBuilder pb = new ProcessBuilder(LD_CONSOLE, "launch", "--name", name);
            pb.start();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean quitInstance(String name) {
        try {
            ProcessBuilder pb = new ProcessBuilder(LD_CONSOLE, "quit", "--name", name);
            pb.start();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
