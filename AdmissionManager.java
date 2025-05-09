import java.awt.*;
import java.io.*;
import javax.swing.*;

public class AdmissionManager extends JFrame {
    JTextField nameField, departmentField;
    JButton addButton, viewButton, deleteButton, backButton;
    JTextArea displayArea;

    public AdmissionManager() {
        setTitle("Manage Admissions");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(3, 2));
        inputPanel.add(new JLabel("Patient Name:"));
        nameField = new JTextField();
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Department:"));
        departmentField = new JTextField();
        inputPanel.add(departmentField);

        addButton = new JButton("Add Admission");
        deleteButton = new JButton("Delete Admission");
        inputPanel.add(addButton);
        inputPanel.add(deleteButton);

        add(inputPanel, BorderLayout.NORTH);

        displayArea = new JTextArea();
        displayArea.setEditable(false);
        add(new JScrollPane(displayArea), BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        viewButton = new JButton("View Admissions");
        backButton = new JButton("Back to Dashboard");
        bottomPanel.add(viewButton);
        bottomPanel.add(backButton);
        add(bottomPanel, BorderLayout.SOUTH);

        addButton.addActionListener(e -> addAdmission());
        viewButton.addActionListener(e -> viewAdmissions());
        deleteButton.addActionListener(e -> deleteAdmission());
        backButton.addActionListener(e -> {
            dispose();
            new Dashboard(); 
        });

        setVisible(true);
    }

    private void addAdmission() {
        String name = nameField.getText().trim();
        String department = departmentField.getText().trim();
        if (name.isEmpty() || department.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Both fields are required.");
            return;
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter("data/admissions.txt", true))) {
            bw.write(name + "," + department);
            bw.newLine();
            JOptionPane.showMessageDialog(this, "Admission added successfully.");
            nameField.setText("");
            departmentField.setText("");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error writing to file.");
        }
    }

    private void viewAdmissions() {
        displayArea.setText("");
        try (BufferedReader br = new BufferedReader(new FileReader("data/admissions.txt"))) {
            String line;
            displayArea.append("Name\t\tDepartment\n");
            displayArea.append("------------------------------\n");
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    displayArea.append(parts[0] + "\t\t" + parts[1] + "\n");
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error reading file.");
        }
    }

    private void deleteAdmission() {
        String nameToDelete = nameField.getText().trim();
        if (nameToDelete.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter patient name to delete.");
            return;
        }

        File inputFile = new File("data/admissions.txt");
        File tempFile = new File("data/admissions_temp.txt");

        boolean found = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                String[] parts = currentLine.split(",");
                if (parts.length == 2 && parts[0].trim().equalsIgnoreCase(nameToDelete)) {
                    found = true;
                    continue; // skip writing this line
                }
                writer.write(currentLine);
                writer.newLine();
            }

        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error reading or writing file.");
            return;
        }

        if (found) {
            if (!inputFile.delete() || !tempFile.renameTo(inputFile)) {
                JOptionPane.showMessageDialog(this, "Could not update admissions file.");
                return;
            }
            JOptionPane.showMessageDialog(this, "Admission deleted successfully.");
            nameField.setText("");
            departmentField.setText("");
        } else {
            tempFile.delete();
            JOptionPane.showMessageDialog(this, "Admission not found.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(AdmissionManager::new);
    }
}
