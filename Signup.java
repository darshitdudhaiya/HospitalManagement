import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class Signup extends JFrame {
    JTextField usernameField;
    JPasswordField passwordField;
    JButton registerButton, backButton;

    public Signup() {
        setTitle("Signup");
        setSize(300, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 1));

        usernameField = new JTextField();
        passwordField = new JPasswordField();

        registerButton = new JButton("Register");
        backButton = new JButton("Back to Login");

        add(new JLabel("New Username:"));
        add(usernameField);
        add(new JLabel("New Password:"));
        add(passwordField);
        add(registerButton);
        add(backButton);

        registerButton.addActionListener(e -> signup());
        backButton.addActionListener(e -> {
            dispose();
            new Login();
        });

        setVisible(true);
    }

    private void signup() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Fields cannot be empty.");
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader("data/users.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 1 && parts[0].equals(username)) {
                    JOptionPane.showMessageDialog(this, "Username already exists.");
                    return;
                }
            }
        } catch (IOException e) {
           
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter("data/users.txt", true))) {
            bw.write(username + "," + password);
            bw.newLine();
            JOptionPane.showMessageDialog(this, "Registration successful!");
            dispose();
            new Login();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error writing user data.");
        }
    }
}
