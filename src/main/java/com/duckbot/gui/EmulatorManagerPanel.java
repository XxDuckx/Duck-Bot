package com.duckbot.gui;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;
import com.duckbot.runner.BotRunner;
import com.duckbot.script.ScriptManager;
import com.duckbot.script.ScriptManager.ScriptData;
import com.duckbot.core.*;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;


public class EmulatorManagerPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private final JButton manageScriptsBtn = new JButton("Manage Scripts");

    private final ScriptManager scriptManager;
    private final List<Instance> instances = new ArrayList<>();

    public EmulatorManagerPanel(ScriptManager scriptManager) {
        super(new BorderLayout());
        this.scriptManager = scriptManager;

        manageScriptsBtn.addActionListener(e -> {
            Window window = SwingUtilities.getWindowAncestor(this);
            if (window instanceof Frame) {
                new ManageScriptsDialog((Frame) window, scriptManager).setVisible(true);
            }
        });

        add(manageScriptsBtn, BorderLayout.NORTH);
    }

    public static class Instance {
        final String displayName;
        final String deviceId;
        List<String> scriptQueue = new ArrayList<>();
        int currentIndex = 0;

        Instance(String displayName, String deviceId) {
            this.displayName = displayName;
            this.deviceId = deviceId;
        }
    }
}
