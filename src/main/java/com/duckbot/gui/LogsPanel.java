package com.duckbot.gui;

import javax.swing.*;
import java.awt.*;

public class LogsPanel extends JPanel {
    private final JTextArea logArea = new JTextArea();

    public LogsPanel() {
        setLayout(new BorderLayout());
        setBackground(new Color(30, 30, 30));

        logArea.setEditable(false);
        logArea.setBackground(new Color(20, 20, 20));
        logArea.setForeground(Color.WHITE);
        logArea.setFont(new Font("Consolas", Font.PLAIN, 12));

        JScrollPane scrollPane = new JScrollPane(logArea);
        scrollPane.getViewport().setBackground(new Color(30, 30, 30));
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(60, 60, 60)));

        add(scrollPane, BorderLayout.CENTER);
    }

    public void appendLog(String message) {
        logArea.append(message + "\n");
        logArea.setCaretPosition(logArea.getDocument().getLength());
    }
}
