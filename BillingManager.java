import java.awt.*;
import java.io.*;
import java.util.*;
import javax.swing.*;

public class BillingManager extends JFrame {
    JComboBox<String> patientComboBox;
    JButton generateBillButton, backButton;
    JTextArea billArea;

    public BillingManager() {
        setTitle("Billing Manager");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new GridLayout(2, 2));
        topPanel.add(new JLabel("Select Patient:"));
        patientComboBox = new JComboBox<>();
        loadPatients();
        topPanel.add(patientComboBox);

        generateBillButton = new JButton("Generate Bill");
        topPanel.add(generateBillButton);

        backButton = new JButton("Back");
        topPanel.add(backButton);

        add(topPanel, BorderLayout.NORTH);

        billArea = new JTextArea();
        billArea.setEditable(false);
        add(new JScrollPane(billArea), BorderLayout.CENTER);

        generateBillButton.addActionListener(e -> generateBill());
        backButton.addActionListener(e -> {
            dispose();
            new Dashboard();
        });

        setVisible(true);
    }

    private void loadPatients() {
        patientComboBox.removeAllItems();
        File file = new File("data/admissions.txt");
        if (!file.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 1) {
                    patientComboBox.addItem(parts[0].trim());
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error loading patients.");
        }
    }

    private void generateBill() {
        String patientName = (String) patientComboBox.getSelectedItem();
        if (patientName == null) {
            JOptionPane.showMessageDialog(this, "No patient selected.");
            return;
        }

        int total = 0;
        ArrayList<String> medicines = new ArrayList<>();

        File pharmacyFile = new File("data/pharmacy_assignments.txt");
        if (pharmacyFile.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(pharmacyFile))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts.length >= 2 && parts[0].equalsIgnoreCase(patientName)) {
                        medicines.add(parts[1] + " (" + (parts.length == 3 ? parts[2] : "Dosage N/A") + ")");
                        total += 100; // Dummy cost per medicine
                    }
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error reading pharmacy data.");
                return;
            }
        }

        // Display bill
        billArea.setText("Patient: " + patientName + "\n");
        billArea.append("===================================\n");
        if (medicines.isEmpty()) {
            billArea.append("No medicines assigned.\n");
        } else {
            for (String med : medicines) {
                billArea.append("Medicine: " + med + " - 100\n");
            }
            billArea.append("===================================\n");
            billArea.append("Total Amount: " + total + "\n");
        }
    }}