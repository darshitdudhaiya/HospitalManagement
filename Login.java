import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class Login extends JFrame {
    JTextField usernameField;
    JPasswordField passwordField;
    JButton loginButton, signupButton;

    public Login() {
        setTitle("Login");
        setSize(300, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 1));

        usernameField = new JTextField();
        passwordField = new JPasswordField();

        loginButton = new JButton("Login");
        signupButton = new JButton("Signup");

        add(new JLabel("Username:"));
        add(usernameField);
        add(new JLabel("Password:"));
        add(passwordField);
        add(loginButton);
        add(signupButton);

        loginButton.addActionListener(e -> login());
        signupButton.addActionListener(e -> {
            dispose(); 
            new Signup(); 
        });

        setVisible(true);
    }

    private void login() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());

        try (BufferedReader br = new BufferedReader(new FileReader("data/users.txt"))) {
            String line;
            boolean found = false;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2 && parts[0].equals(username) && parts[1].equals(password)) {
                    found = true;
                    break;
                }
            }

            if (found) {
                JOptionPane.showMessageDialog(this, "Login successful!");
                dispose();
                new Dashboard(); 
            } else {
                JOptionPane.showMessageDialog(this, "Invalid credentials.");
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error reading user data.");
        }
    }
}
