package com.duckbot.manager;

public class Account {
    private String email;
    private String password;

    // Constructor to initialize the account with email and password
    public Account(String email, String password) {
        this.email = email;
        this.password = password;
    }

    // Getter for email
    public String getEmail() {
        return email;
    }

    // Setter for email
    public void setEmail(String email) {
        this.email = email;
    }

    // Getter for password
    public String getPassword() {
        return password;
    }

    // Setter for password
    public void setPassword(String password) {
        this.password = password;
    }

    // Override toString for easy printing
    @Override
    public String toString() {
        return "Account{" +
                "email='" + email + '\'' +
                '}';
    }
}
