package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class LoginScreen extends JFrame {
    DataAccess dataAccess = new DataAccess();

    public LoginScreen() {

        setTitle("Login");
        setSize(300, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        login();
    }

    private void login() {
        JPanel panel = new JPanel(new GridLayout(3, 2));

        panel.add(new JLabel("Username:"));
        JTextField usernameField = new JTextField();
        panel.add(usernameField);

        panel.add(new JLabel("Password:"));
        JPasswordField passwordField = new JPasswordField();
        panel.add(passwordField);

        JButton loginButton = new JButton("Login");
        panel.add(loginButton);

        loginButton.addActionListener((ActionEvent e) -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            User user = dataAccess.loadUser(username, password);
            if (user == null) {
                JOptionPane.showMessageDialog(null, "Incorrect username or password!");
            } else {
                // If user exists, hide the login window and proceed to next screen
                System.out.println("Logging in with username = " + username + " and password = " + password);
                this.setVisible(false);
                // dataAccess.closeConnection();
                EventQueue.invokeLater(() -> {
                    MainWindow application = new MainWindow(user);
                    application.setVisible(true);
                });
            }

        });

        add(panel);
    }
}