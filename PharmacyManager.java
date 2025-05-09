import java.awt.*;
import java.io.*;
import javax.swing.*;

public class PharmacyManager extends JFrame {
    JTextField dosageField;
    JComboBox<String> medicineComboBox, patientComboBox;
    JButton assignButton, viewButton, backButton;
    JTextArea displayArea;

    public PharmacyManager() {
        setTitle("Manage Pharmacy");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(4, 2));
        
        patientComboBox = new JComboBox<>();
        loadPatientIntoComboBox();
        inputPanel.add(new JLabel("Patient Name:"));
        inputPanel.add(patientComboBox); 

        inputPanel.add(new JLabel("Medicine:"));
        medicineComboBox = new JComboBox<>();
        loadMedicinesIntoComboBox();
        inputPanel.add(medicineComboBox);

        inputPanel.add(new JLabel("Dosage Instructions:"));
        dosageField = new JTextField();
        inputPanel.add(dosageField);

        assignButton = new JButton("Assign Medicine");
        inputPanel.add(assignButton);

        backButton = new JButton("Back");
        inputPanel.add(backButton);

        add(inputPanel, BorderLayout.NORTH);

        displayArea = new JTextArea();
        displayArea.setEditable(false);
        add(new JScrollPane(displayArea), BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        viewButton = new JButton("View Assignments");
        bottomPanel.add(viewButton);
        add(bottomPanel, BorderLayout.SOUTH);

        assignButton.addActionListener(e -> assignMedicine());
        viewButton.addActionListener(e -> viewAssignments());
        backButton.addActionListener(e -> {
            dispose();
            new Dashboard();
        });

        setVisible(true);
    }

    private void loadPatientIntoComboBox() {
        patientComboBox.removeAllItems();
        File file = new File("data/admissions.txt");
        if (!file.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    patientComboBox.addItem(parts[0].trim());
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error loading patients.");
        }
    }

    private void loadMedicinesIntoComboBox() {
        medicineComboBox.removeAllItems();
        File file = new File("data/medicines.txt");
        if (!file.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 1) {
                    medicineComboBox.addItem(parts[0].trim());
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error loading medicines.");
        }
    }

    private void assignMedicine() {
        String patient = (String) patientComboBox.getSelectedItem();
        String medicine = (String) medicineComboBox.getSelectedItem();
        String dosage = dosageField.getText().trim();

        if (patient == null || medicine == null || dosage.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required.");
            return;
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter("data/pharmacy_assignments.txt", true))) {
            bw.write(patient + "," + medicine + "," + dosage);
            bw.newLine();
            JOptionPane.showMessageDialog(this, "Medicine assigned successfully.");
            dosageField.setText("");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error writing to file.");
        }
    }

    private void viewAssignments() {
        displayArea.setText("");
        try (BufferedReader br = new BufferedReader(new FileReader("data/pharmacy_assignments.txt"))) {
            String line;
            displayArea.append("Patient\t\tMedicine\t\tDosage\n");
            displayArea.append("----------------------------------------------------\n");
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    displayArea.append(parts[0] + "\t\t" + parts[1] + "\t\t" + parts[2] + "\n");
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error reading file.");
        }
    }
}
