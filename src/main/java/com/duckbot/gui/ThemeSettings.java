package com.duckbot.gui;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javax.swing.*;
import java.awt.*;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ThemeSettings {

    private static final String THEME_FOLDER_PATH = "./themes/";
    private static final String CONFIG_FILE_PATH = "./config/selected_theme.json";

    // Method to create the UI components for theme settings
    public static JPanel createThemeSettingsPanel(JFrame parentFrame) {
        JPanel settingsPanel = new JPanel();
        settingsPanel.setLayout(new BorderLayout());

        // Combo box for theme selection
        JPanel settingsControls = new JPanel();
        settingsPanel.add(settingsControls, BorderLayout.NORTH);

        JLabel themeLabel = new JLabel("Select Theme:");
        settingsControls.add(themeLabel);

        JComboBox<String> themeComboBox = new JComboBox<>();
        themeComboBox.addItem("Default");
        themeComboBox.addItem("Dark Mode");
        themeComboBox.addItem("Light Mode");

        // Get list of saved themes from the file
        for (String theme : getSavedThemes()) {
            themeComboBox.addItem(theme);
        }

        settingsControls.add(themeComboBox);

        // Create a panel for the custom theme inputs
        JPanel customThemePanel = new JPanel();
        customThemePanel.setLayout(new BoxLayout(customThemePanel, BoxLayout.Y_AXIS)); // Vertical layout

        JTextField themeNameField = new JTextField(10);
        JTextField bgColorField = new JTextField(10);
        JTextField textColorField = new JTextField(10);
        JTextField buttonColorField = new JTextField(10);
        JTextField fontColorField = new JTextField(10);
        JTextField fontSizeField = new JTextField(10);
        JTextField buttonHoverColorField = new JTextField(10);
        JComboBox<String> fontStyleComboBox = new JComboBox<>(new String[]{"Plain", "Bold", "Italic", "Bold Italic"});

        // Add labels and fields to the panel
        customThemePanel.add(new JLabel("Theme Name:"));
        customThemePanel.add(themeNameField);
        customThemePanel.add(new JLabel("Background Color (Hex):"));
        customThemePanel.add(bgColorField);
        customThemePanel.add(new JLabel("Text Color (Hex):"));
        customThemePanel.add(textColorField);
        customThemePanel.add(new JLabel("Button Color (Hex):"));
        customThemePanel.add(buttonColorField);
        customThemePanel.add(new JLabel("Font Color (Hex):"));
        customThemePanel.add(fontColorField);
        customThemePanel.add(new JLabel("Font Size (px):"));
        customThemePanel.add(fontSizeField);
        customThemePanel.add(new JLabel("Button Hover Color (Hex):"));
        customThemePanel.add(buttonHoverColorField);
        customThemePanel.add(new JLabel("Font Style:"));
        customThemePanel.add(fontStyleComboBox);

        JScrollPane scrollPane = new JScrollPane(customThemePanel);
        settingsPanel.add(scrollPane, BorderLayout.CENTER);

        // Save theme button
        JButton saveThemeButton = new JButton("Save Custom Theme");
        settingsControls.add(saveThemeButton);

        saveThemeButton.addActionListener(e -> {
            String themeName = themeNameField.getText();
            String bgColor = bgColorField.getText();
            String textColor = textColorField.getText();
            String buttonColor = buttonColorField.getText();
            String fontColor = fontColorField.getText();
            String fontSize = fontSizeField.getText();
            String buttonHoverColor = buttonHoverColorField.getText();
            String fontStyle = (String) fontStyleComboBox.getSelectedItem();

            if (!themeName.isEmpty() && !bgColor.isEmpty() && !textColor.isEmpty() && !buttonColor.isEmpty()) {
                saveCustomTheme(bgColor, textColor, buttonColor, fontColor, fontSize, buttonHoverColor, fontStyle, themeName);
                JOptionPane.showMessageDialog(parentFrame, "Custom theme saved successfully!");
                themeComboBox.addItem(themeName);  // Add custom theme to the combo box
            }
        });

        themeComboBox.addActionListener(e -> {
            String selectedTheme = (String) themeComboBox.getSelectedItem();
            applyTheme(selectedTheme, parentFrame);  // Apply the selected theme
        });

        return settingsPanel;
    }

    // Apply the selected theme from saved file or default theme
    public static void applyTheme(String themeName, Component rootComponent) {
        JsonObject theme = loadThemeFromFile(themeName);
        if (theme != null) {
            JsonObject colors = theme.getAsJsonObject("colors");
            JsonObject ui = theme.getAsJsonObject("ui");

            // Apply the colors to the UI
            UIManager.put("Panel.background", Color.decode(colors.get("bg0").getAsString()));
            UIManager.put("Label.foreground", Color.decode(colors.get("fg0").getAsString()));
            UIManager.put("Button.background", Color.decode(colors.get("bg2").getAsString()));
            UIManager.put("Button.foreground", Color.decode(colors.get("fg0").getAsString()));
            UIManager.put("TextField.background", Color.decode(colors.get("bg1").getAsString()));
            UIManager.put("TextField.foreground", Color.decode(colors.get("fg0").getAsString()));

            // Update the UI components
            SwingUtilities.updateComponentTreeUI(rootComponent);
        }
    }

    // Load the theme from a JSON file
    public static JsonObject loadThemeFromFile(String themeName) {
        try {
            FileReader reader = new FileReader(THEME_FOLDER_PATH + themeName + ".json");
            return JsonParser.parseReader(reader).getAsJsonObject();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Save the custom theme to a file
    public static void saveCustomTheme(String bgColor, String textColor, String buttonColor, String fontColor, String fontSize,
                                       String buttonHoverColor, String fontStyle, String themeName) {
        JsonObject theme = new JsonObject();
        JsonObject colors = new JsonObject();

        // Store the custom colors
        colors.addProperty("bg0", bgColor);
        colors.addProperty("fg0", textColor);
        colors.addProperty("bg2", buttonColor);
        colors.addProperty("fontColor", fontColor);
        colors.addProperty("fontSize", fontSize);
        colors.addProperty("buttonHoverColor", buttonHoverColor);
        colors.addProperty("fontStyle", fontStyle);

        theme.add("colors", colors);

        // Write theme to JSON file
        try (FileWriter writer = new FileWriter(THEME_FOLDER_PATH + themeName + ".json")) {
            writer.write(theme.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
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
