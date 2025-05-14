package com.duckbot.gui;

import com.duckbot.script.ScriptManager;
import javax.swing.*;
import java.awt.*;
import java.util.List;


public class ManageScriptsDialog extends JDialog {

    public final ScriptManager scriptManager;

    public ManageScriptsDialog(Window parent, ScriptManager scriptManager) {
        super(parent, "Manage Scripts", ModalityType.APPLICATION_MODAL);
        this.scriptManager = scriptManager;

        setLayout(new FlowLayout());

        // Fetch all script names to populate the combo box
        List<String> scriptNames = scriptManager.getScriptNames();

        // Create a combo box to display script names
        JComboBox<String> scriptComboBox = new JComboBox<>();
        for (String scriptName : scriptNames) {
            scriptComboBox.addItem(scriptName);
        }
        add(scriptComboBox);

        // Apply Button to apply the selected script
        JButton applyButton = new JButton("Apply Script");
        applyButton.addActionListener(e -> {
            String selectedScript = (String) scriptComboBox.getSelectedItem();
            if (selectedScript != null) {
                scriptManager.applyScript(selectedScript);  // Apply the selected script
            }
        });
        add(applyButton);

        setSize(300, 150);
        setLocationRelativeTo(parent);  // Centers the dialog on the parent window
    }
}
