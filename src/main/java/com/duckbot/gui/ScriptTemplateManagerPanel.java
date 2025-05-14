package com.duckbot.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;

public class ScriptTemplateManagerPanel extends JPanel {
    private final DefaultListModel<String> templateListModel = new DefaultListModel<>();
    private final JTextArea scriptArea = new JTextArea();
    private final JTextField templateNameField = new JTextField(20);
    private final JComboBox<String> gameSelector = new JComboBox<>(new String[]{"WestGame", "FarmSim", "Other"});
    private final File templateFolder = new File("script-templates");

    public ScriptTemplateManagerPanel() {
        setLayout(new BorderLayout());

        JPanel leftPanel = new JPanel(new BorderLayout());
        JList<String> templateList = new JList<>(templateListModel);
        JScrollPane listScroll = new JScrollPane(templateList);
        leftPanel.add(new JLabel("Templates:"), BorderLayout.NORTH);
        leftPanel.add(listScroll, BorderLayout.CENTER);

        JPanel editorPanel = new JPanel(new BorderLayout());
        JPanel topEditor = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topEditor.add(new JLabel("Name:"));
        topEditor.add(templateNameField);
        topEditor.add(new JLabel("Game:"));
        topEditor.add(gameSelector);
        JButton saveBtn = new JButton("Save Template");
        JButton deleteBtn = new JButton("Delete Template");
        topEditor.add(saveBtn);
        topEditor.add(deleteBtn);
        editorPanel.add(topEditor, BorderLayout.NORTH);

        scriptArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        editorPanel.add(new JScrollPane(scriptArea), BorderLayout.CENTER);

        add(leftPanel, BorderLayout.WEST);
        add(editorPanel, BorderLayout.CENTER);

        loadTemplateList();

        templateList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String selected = templateList.getSelectedValue();
                if (selected != null) {
                    loadTemplate(selected);
                }
            }
        });

        saveBtn.addActionListener(this::saveTemplate);
        deleteBtn.addActionListener(this::deleteTemplate);
    }

    private void loadTemplateList() {
        templateListModel.clear();
        if (!templateFolder.exists()) templateFolder.mkdirs();
        File[] files = templateFolder.listFiles((dir, name) -> name.endsWith(".txt"));
        if (files != null) {
            for (File f : files) {
                templateListModel.addElement(f.getName().replace(".txt", ""));
            }
        }
    }

    private void loadTemplate(String name) {
        File file = new File(templateFolder, name + ".txt");
        try {
            scriptArea.setText(new String(java.nio.file.Files.readAllBytes(file.toPath())));
            templateNameField.setText(name);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Failed to load template: " + e.getMessage());
        }
    }

    private void saveTemplate(ActionEvent e) {
        String name = templateNameField.getText().trim();
        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a template name.");
            return;
        }
        String game = (String) gameSelector.getSelectedItem();
        File outFile = new File(templateFolder, name + ".txt");
        try (PrintWriter writer = new PrintWriter(outFile)) {
            writer.write(scriptArea.getText());
            loadTemplateList();

            // Auto-export to scripts/<Game>/ folder
            File gameDir = new File("scripts/" + game);
            gameDir.mkdirs();
            File gameCopy = new File(gameDir, name + ".txt");
            try (PrintWriter gameWriter = new PrintWriter(gameCopy)) {
                gameWriter.write(scriptArea.getText());
            }

            JOptionPane.showMessageDialog(this, "Template saved and exported to game folder.");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Failed to save template: " + ex.getMessage());
        }
    }

    private void deleteTemplate(ActionEvent e) {
        String name = templateNameField.getText().trim();
        if (name.isEmpty()) return;
        File file = new File(templateFolder, name + ".txt");
        if (file.exists() && file.delete()) {
            loadTemplateList();
            scriptArea.setText("");
            templateNameField.setText("");
        } else {
            JOptionPane.showMessageDialog(this, "Failed to delete template.");
        }
    }
}
