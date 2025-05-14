package com.duckbot.gui;

import javax.swing.*;
import java.awt.*;

public class LoginDialog extends JDialog {

    private JTextField emailField;
    private JPasswordField passwordField;

    // Constructor accepting Window (parent component)
    public LoginDialog(Window parent) {
        super(parent, "Login", ModalityType.APPLICATION_MODAL);
        setLayout(new FlowLayout());

        // Create and add components for email and password input
        emailField = new JTextField(20);
        passwordField = new JPasswordField(20);
        
        add(new JLabel("Email:"));
        add(emailField);
        add(new JLabel("Password:"));
        add(passwordField);

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(e -> {
            // Handle login logic here
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());
            System.out.println("Logged in as: " + email);
            // Close dialog after login
            dispose();
        });
        
        add(loginButton);

        setSize(300, 200);
        setLocationRelativeTo(parent); // Centers the dialog on the parent window
    }

    // Getters for email and password
    public String getEmail() {
        return emailField.getText();
    }

    public String getPassword() {
        return new String(passwordField.getPassword());
    }
}
