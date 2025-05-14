package com.duckbot.settings;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javax.swing.*;
import java.awt.*;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class SettingsManager {

    private static final String THEME_FOLDER_PATH = "./themes/";
    private static final String CONFIG_FILE_PATH = "./config/selected_theme.json";

    // Apply a theme from a file
    public static void applyTheme(JFrame frame, String themeName) {
        JsonObject theme = loadThemeFromFile(themeName);
        if (theme != null) {
            JsonObject colors = theme.getAsJsonObject("colors");
            JsonObject ui = theme.getAsJsonObject("ui");

            // Apply the colors to the UI
            UIManager.put("Panel.background", Color.decode(colors.get("bg0").getAsString()));
            UIManager.put("Label.foreground", Color.decode(colors.get("fg0").getAsString()));
            UIManager.put("Button.background", Color.decode(colors.get("bg2").getAsString()));
            UIManager.put("Button.foreground", Color.decode(colors.get("fg0").getAsString()));

            // Update the UI components
            SwingUtilities.updateComponentTreeUI(frame);
        }
    }

    // Load a theme from a file
    private static JsonObject loadThemeFromFile(String themeName) {
        try {
            FileReader reader = new FileReader(THEME_FOLDER_PATH + themeName + ".json");
            return JsonParser.parseReader(reader).getAsJsonObject();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Get the list of saved theme names
    public static List<String> getSavedThemes() {
        List<String> themes = new ArrayList<>();
        try {
            Files.walk(Paths.get(THEME_FOLDER_PATH))
                    .filter(path -> path.toString().endsWith(".json"))
                    .forEach(path -> themes.add(path.getFileName().toString().replace(".json", "")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return themes;
    }

    // Save the selected theme name
    public static void saveSelectedTheme(String themeName) {
        JsonObject config = new JsonObject();
        config.addProperty("selected_theme", themeName);

        try (FileWriter writer = new FileWriter(CONFIG_FILE_PATH)) {
            writer.write(config.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Load the selected theme name
    public static String loadSelectedTheme() {
        try {
            FileReader reader = new FileReader(CONFIG_FILE_PATH);
            JsonObject config = JsonParser.parseReader(reader).getAsJsonObject();
            return config.get("selected_theme").getAsString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
