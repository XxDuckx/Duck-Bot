package com.duckbot.gui;

import com.duckbot.scripting.Script;
import com.duckbot.core.EmulatorInstance;
import com.duckbot.utils.ScriptLoader;
import com.duckbot.utils.commands.ScriptCommand;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;

public class ScriptPreviewPanel extends JPanel {
    private final JTextArea outputArea = new JTextArea();
    private Script currentScript;

    public ScriptPreviewPanel() {
        setLayout(new BorderLayout());

        JButton loadBtn = new JButton("Load Script File");
        JButton runBtn = new JButton("Dry Run Script");

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        top.add(loadBtn);
        top.add(runBtn);

        outputArea.setEditable(false);
        JScrollPane scroll = new JScrollPane(outputArea);

        add(top, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);

        loadBtn.addActionListener(this::onLoadScript);
        runBtn.addActionListener(this::onDryRun);
    }

    private void onLoadScript(ActionEvent e) {
        JFileChooser chooser = new JFileChooser(new File("scripts"));
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                File file = chooser.getSelectedFile();
                currentScript = ScriptLoader.load(file);
                outputArea.setText("== Parsed Script ==\n");
                for (ScriptCommand cmd : currentScript.getCommands()) {
                    outputArea.append(cmd.toString() + "\n");
                }
            } catch (Exception ex) {
                outputArea.setText("❌ Error loading script:\n" + ex.getMessage());
                JOptionPane.showMessageDialog(this,
                        "Script parsing failed:\n" + ex.getMessage(),
                        "Parse Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void onDryRun(ActionEvent e) {
        if (currentScript == null) {
            outputArea.setText("No script loaded.");
            return;
        }
        outputArea.append("\n== Dry Run Output ==\n");
        try {
            EmulatorInstance mock = new EmulatorInstance("mock") {
                @Override
                public void tap(int x, int y) { log("TAP " + x + " " + y); }
                @Override
                public void input(String text) { log("INPUT " + text); }
                @Override
                public void pressBack() { log("BACK"); }
                @Override
                public void pressHome() { log("HOME"); }
                @Override
                public void closePopup() { log("CLOSE_POPUP"); }
                @Override
                public boolean findImage(String path) { log("FIND_IMAGE " + path); return true; }

                public void log(String message) {
                    outputArea.append("[MOCK] " + message + "\n");
                }
            };

            for (ScriptCommand cmd : currentScript.getCommands()) {
                cmd.execute(mock);
            }
        } catch (Exception ex) {
            outputArea.append("Error during dry run: " + ex.getMessage());
        }
    }
}
