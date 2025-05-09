# 🏥 Hospital Management System – Pharmacy Module

This is a Java Swing-based desktop application that manages pharmacy-related operations for a hospital. It includes features to assign medicines to patients, store assignments, and view prescription history using a simple GUI and local file storage.

---

## 📦 Features

- 🔍 View available medicines
- 🧾 Assign medicine to admitted patients with dosage instructions
- 📋 View all pharmacy assignments
- 💾 Stores data in local text files
- 🪟 Built using Java Swing

---

## 📁 Project Structure

```
HospitalPharmacySystem/
│
├── data/
│ ├── medicines.txt # List of available medicines
│ ├── admissions.txt # Patient admission records
│ └── pharmacy_assignments.txt # Assigned medicine records
│
├── Dashboard.java # Main dashboard window
├── PharmacyManager.java # Pharmacy module GUI and logic
├── BillingManager.java # (Optional) For future billing features
└── README.md # This file
```

---

## 🛠️ How to Run

### Prerequisites

- Java Development Kit (JDK) 8 or higher
- A Java-compatible IDE (e.g., IntelliJ, Eclipse, or NetBeans)

### Steps

1. Clone or download this repository.
2. Ensure the following folder and files exist:
   - `data/medicines.txt`
   - `data/admissions.txt`
3. Compile the `.java` files using your IDE or from the terminal:

```bash
javac Main.java
java Main
```

---


## 🧪 Sample Dummy Data

### 📄 data/medicines.txt

```
Paracetamol,50
Amoxicillin,75
Ibuprofen,60
Cetirizine,30
Azithromycin,90
Pantoprazole,55
```

### 📄 data/admissions.txt

```
John Doe,1001
Jane Smith,1002
Raj Patel,1003
Priya Mehta,1004
```

---

## 👨‍💻 Author

Created by **Darshit Dudhaiya**

---

Let me know if you'd like a version in plain text or Word format as well.

