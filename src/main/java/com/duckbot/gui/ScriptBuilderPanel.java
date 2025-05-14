package com.duckbot.gui;

import com.duckbot.utils.ScriptBuilder;
import com.duckbot.utils.Script;
import com.duckbot.utils.ScriptLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.List;

public class ScriptBuilderPanel extends JPanel {
    private final ScriptBuilder builder = new ScriptBuilder();
    private final DefaultListModel<String> scriptListModel = new DefaultListModel<>();
    private final JList<String> scriptList = new JList<>(scriptListModel);
    private final JTextArea scriptPreviewArea = new JTextArea();

    public ScriptBuilderPanel() {
        setLayout(new BorderLayout());

        JPanel topBar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton loadScriptBtn = new JButton("Load Script");
        JButton removeScriptBtn = new JButton("Remove Selected");
        JButton clearBtn = new JButton("Clear All");

        loadScriptBtn.addActionListener(this::onLoadScript);
        removeScriptBtn.addActionListener(e -> {
            int index = scriptList.getSelectedIndex();
            List<Script> scripts = builder.getScripts();
            if (index >= 0 && index < scripts.size()) {
                scriptListModel.remove(index);
                scripts.remove(index);
                updatePreview();
            }
        });
        clearBtn.addActionListener(e -> {
            builder.getScripts().clear();
            scriptListModel.clear();
            scriptPreviewArea.setText("");
        });

        topBar.add(loadScriptBtn);
        topBar.add(removeScriptBtn);
        topBar.add(clearBtn);
        add(topBar, BorderLayout.NORTH);

        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                new JScrollPane(scriptList), new JScrollPane(scriptPreviewArea));
        scriptPreviewArea.setEditable(false);
        add(split, BorderLayout.CENTER);
    }

    public static void registerInTabbedPane(JTabbedPane tabbedPane) {
        tabbedPane.addTab("Script Builder", new ScriptBuilderPanel());
    }

    public static void registerInMainFrame(MainFrame frame) {
        if (frame != null) {
            JTabbedPane tabs = frame.getTabbedPane();
            if (tabs != null) {
                registerInTabbedPane(tabs);
            }
        }
    }

    static {
        SwingUtilities.invokeLater(() -> {
            Frame[] frames = Frame.getFrames();
            for (Frame f : frames) {
                if (f instanceof MainFrame) {
                    registerInMainFrame((MainFrame) f);
                    break;
                }
            }
        });
    }

    private void onLoadScript(ActionEvent e) {
        JFileChooser chooser = new JFileChooser(new File("scripts"));
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                File file = chooser.getSelectedFile();
                Script script = ScriptLoader.load(file);
                builder.addScript(script);
                scriptListModel.addElement(script.getName());
                updatePreview();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Failed to load script: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void updatePreview() {
        StringBuilder sb = new StringBuilder();
        for (Script s : builder.getScripts()) {
            sb.append("== ").append(s.getName()).append(" ==\n");
            if (s.getCommands() != null) {
                s.getCommands().forEach(cmd -> sb.append(cmd.toString()).append("\n"));
            }
            sb.append("\n");
        }
        scriptPreviewArea.setText(sb.toString());
    }

    public ScriptBuilder getBuilder() {
        return builder;
    }
}
