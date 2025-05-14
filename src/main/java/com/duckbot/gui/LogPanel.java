package com.duckbot.gui;

import javax.swing.*;
import java.awt.*;

public class LogPanel extends JPanel {

    public LogPanel() {
        setLayout(new BorderLayout());

        // Example components for logs
        JTextArea logArea = new JTextArea(6, 50);
        logArea.setEditable(false);
        logArea.setBackground(Color.BLACK);
        logArea.setForeground(Color.WHITE);
        JScrollPane scrollPane = new JScrollPane(logArea);

        add(scrollPane, BorderLayout.CENTER);
    }
}
