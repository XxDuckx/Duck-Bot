package com.duckbot.gui;

import com.duckbot.utils.ScriptLoader;
import com.duckbot.scripting.Script;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class ScriptValidatorPanel extends JPanel {
    private final JTextArea outputArea = new JTextArea();

    public ScriptValidatorPanel() {
        setLayout(new BorderLayout());
        outputArea.setEditable(false);
        add(new JScrollPane(outputArea), BorderLayout.CENTER);
        validateAllScripts();
    }

    private void validateAllScripts() {
        File scriptDir = new File("scripts");
        if (!scriptDir.exists() || !scriptDir.isDirectory()) {
            outputArea.setText("scripts/ folder not found.");
            return;
        }

        File[] files = scriptDir.listFiles((dir, name) -> name.toLowerCase().endsWith(".txt"));
        if (files == null || files.length == 0) {
            outputArea.setText("No .txt script files found in scripts/.");
            return;
        }

        StringBuilder result = new StringBuilder("== Script Validation Report ==\n\n");

        for (File file : files) {
            try {
                Script script = ScriptLoader.load(file);
                result.append("✅ ").append(file.getName()).append(" loaded successfully.\n");
            } catch (Exception ex) {
                result.append("❌ ").append(file.getName())
                      .append(" failed: ").append(ex.getMessage()).append("\n");
            }
        }

        outputArea.setText(result.toString());
    }
}
