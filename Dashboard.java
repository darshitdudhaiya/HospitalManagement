import java.awt.*;
import javax.swing.*;

public class Dashboard extends JFrame {
    JButton managePatientsButton, managePharmacyButton, manageBillingButton, logoutButton;

    public Dashboard() {
        setTitle("Library Dashboard");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel welcomeLabel = new JLabel("Welcome to Hospital Management System", JLabel.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(welcomeLabel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        managePatientsButton = new JButton("Manage Patients");
        managePharmacyButton = new JButton("Manage Pharmacy");
        manageBillingButton = new JButton("Manage Billing");
        logoutButton = new JButton("Logout");

        centerPanel.add(managePatientsButton);
        centerPanel.add(managePharmacyButton);
        centerPanel.add(manageBillingButton);
        centerPanel.add(logoutButton);

        add(centerPanel, BorderLayout.CENTER);

        managePatientsButton.addActionListener(e -> {
            dispose();
            new AdmissionManager(); // next file we'll create
        });

        managePharmacyButton.addActionListener(e -> {
            dispose();
            new PharmacyManager(); 
        });

        manageBillingButton.addActionListener(e -> {
            dispose();
            new BillingManager(); 
        });

        logoutButton.addActionListener(e -> {
            dispose();
            new Login();
        });

        setVisible(true);
    }
}
