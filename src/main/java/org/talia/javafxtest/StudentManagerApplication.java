// -------------------------------------------------------
// Final Project
// Written by: Talia Muro
// For “Programming 2” Section 1 – Fall 2024
// --------------------------------------------------------

package org.talia.javafxtest;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class StudentManagerApplication {
    @FXML
    private BorderPane borderPane;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private TextField nameField;

    @FXML
    private TextField idField;

    @FXML
    private TextField gpaField;

    @FXML
    private Button addStudentButton;

    @FXML
    private Button searchByIdButton;

    @FXML
    private Button displayAllStudentsButton;

    @FXML
    private Button loadDataButton;

    @FXML
    private Button saveDataButton;

    @FXML
    private Button exitButton;

    @FXML
    private TableView<Student> studentDisplayTable;

    @FXML
    private TableColumn<Student, String> nameDisplay;

    @FXML
    private TableColumn<Student, Integer> idDisplay;

    @FXML
    private TableColumn<Student, Double> gpaDisplay;

    @FXML
    // initialize the ObservableList
    private ObservableList<Student> students;

    @FXML
    // link to the StudentManager class
    private StudentManager studentManager;

    public void initialize() {
        // initialize the ObservableList
        students = FXCollections.observableArrayList();
        studentManager = new StudentManager(students);

        // bind columns to Student properties
        nameDisplay.setCellValueFactory(new PropertyValueFactory<>("name"));
        idDisplay.setCellValueFactory(new PropertyValueFactory<>("id"));
        gpaDisplay.setCellValueFactory(new PropertyValueFactory<>("gpa"));

        // set the items of the TableView
        studentDisplayTable.setItems(students);
    }

    /**
     * Adds student to ObservableList and Displays each object in the TableView along with alert messages
     */
    @FXML
    public void addStudent() {
        // validate the input
        if (nameField.getText().isEmpty() || idField.getText().isEmpty() || gpaField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "All fields must be filled.");
            return;
        }
        try {
            // get the input from the text fields
            String name = nameField.getText();
            // convert id to int
            int id = Integer.parseInt(idField.getText());
            // convert gpa to double
            double gpa = Double.parseDouble(gpaField.getText());

            // check for duplicate ID
            for (Student student : students) {
                if (student.getId() == id) {
                    showAlert(Alert.AlertType.WARNING, "Duplicate ID", "A student with the ID " + id + " already exists.");
                    return;
                }
            }

            // create a new Student object and add it to the students ObservableList
            Student student = new Student(name, id, gpa);
            students.add(student);

            // reset the TableView to the main ObservableList students
            studentDisplayTable.setItems(students);

            // clear the input fields
            nameField.clear();
            idField.clear();
            gpaField.clear();

            // display a message confirming the student has been added
            showAlert(Alert.AlertType.INFORMATION, "Student Added", "Student has been successfully added.");
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Input Error", "Invalid ID or GPA format. Please enter valid numbers.");
        }
    }

    /**
     *
     */
    @FXML
    public void searchById() {
        if (idField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "ID cannot be empty, please fill out the ID field.");
            return;
        }

        try {
            int id = Integer.parseInt(idField.getText());
            boolean studentFound = false;
            // temporary ObservableList that stores the search result
            ObservableList<Student> searchResult = FXCollections.observableArrayList();

            for (Student student : students) {
                if (student.getId() == id) {
                    studentFound = true;
                    searchResult.add(student);
                    break;
                }
            }

            if (studentFound) {
                // update the TableView to display only the student found (searchResult)
                studentDisplayTable.setItems(searchResult);
            } else {
                // display a student not found method in the table view
                studentDisplayTable.setItems(FXCollections.observableArrayList());
                showAlert(Alert.AlertType.INFORMATION, "Not Found", "Student not found.");
                studentDisplayTable.setPlaceholder(new Label("No student with ID: " + id));
            }

            // clear the input fields
            nameField.clear();
            idField.clear();
            gpaField.clear();

        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Input Error", "ID must be an integer.");
        }
    }

    /**
     * Searches for a student based on partial name
     */
    @FXML
    public void searchByName() {
        if (nameField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Name cannot be empty, please fill out the name field.");
            return;
        }

        String partialName = nameField.getText().toLowerCase();
        boolean studentFound = false;
        // temporary ObservableList that stores the search result
        ObservableList<Student> searchResult = FXCollections.observableArrayList();

        // go through each student to look for a match
        for (Student student : students) {
            if (student.getName().toLowerCase().contains(partialName)) {
                searchResult.add(student);
                studentFound = true;
            }
        }

        if (studentFound) {
            // update the TableView to display only the students found (searchResult)
            studentDisplayTable.setItems(searchResult);
            showAlert(Alert.AlertType.INFORMATION, "Student(s) Found", searchResult.size() + " student(s) found matching " + partialName);
        } else {
            // display a student not found method in the table view
            studentDisplayTable.setItems(FXCollections.observableArrayList());
            showAlert(Alert.AlertType.INFORMATION, "Not Found", "Student not found.");
            studentDisplayTable.setPlaceholder(new Label("No students found with name containing: " + partialName));
        }

        // clear the input fields
        nameField.clear();
        idField.clear();
        gpaField.clear();
    }

    /**
     * Displays all students from the ObservableList in the TableView
     */
    @FXML
    public void displayAllStudents() {
        if (students.isEmpty()) {
            studentDisplayTable.setPlaceholder(new Label("No students to display."));
            showAlert(Alert.AlertType.INFORMATION, "No Data", "No students have been added yet.");
        } else {
            studentDisplayTable.setItems(students);
        }

        System.out.println("Number of students: " + students.size());
    }

    /**
     * Saves the data from the Observable list to a text file using format from the saveToFile method in StudentManager class
     */
    @FXML
    public void saveData() {
        // Create a TextInputDialog that allows the user to enter a file name
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Save Data");
        dialog.setHeaderText("Enter a file name");
        dialog.setContentText("File name:");

        // show the dialog and get the user input
        dialog.showAndWait().ifPresent(fileName -> {
            // Validate the file name
            if (fileName.trim().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Invalid Input", "File name cannot be empty.");
            } else if (!fileName.matches("^[a-zA-Z0-9._-]+$")) {
                // Allow only a-z, 0-9, dashes, underscores, and dots
                showAlert(Alert.AlertType.ERROR, "Invalid Input", "File name can only contain letters, numbers, dashes, underscores, and dots.");
            } else {
                // Append ".txt" if not already present
                if (!fileName.endsWith(".txt")) {
                    fileName += ".txt";
                }

                // check if a file with that name already exists
                File file = new File(fileName);
                if (file.exists()) {
                    showAlert(Alert.AlertType.ERROR, "File Exists", "A file with the name " + fileName + " already exists. Please chose a different name.");
                } else {
                    // Save the data using the validated, and non-existing file name
                    studentManager.saveToFile(fileName, students);
                    showAlert(Alert.AlertType.INFORMATION, "Save Data", "Data saved successfully to " + fileName);
                }
            }
        });
    }

    /**
     * Loads data from a text file into an ObservableList and displays it in the TableView
     */
    @FXML
    public void loadData() {
        // create a TextInputDialog for entering the file name
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Load Data");
        dialog.setHeaderText("Enter file name");
        dialog.setContentText("File name:");

        // show the dialog and get the user input
        dialog.showAndWait().ifPresent(fileName -> {
            // validate the file name
            if (fileName.trim().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Invalid Input", "File name cannot be empty.");
            }

            // Allow only alphanumeric characters, dashes, underscores, and dots
            if (!fileName.matches("^[a-zA-Z0-9._-]+$")) {
                showAlert(Alert.AlertType.ERROR, "Invalid Input", "File name can only contain letters, numbers, dashes (-), underscores (_), and dots (.)");
            }

            // Append ".txt" if not already present
            if (!fileName.endsWith(".txt")) {
                fileName += ".txt";
            }

            // check if a file with this name exists
            File file = new File(fileName);
            if (!file.exists()) {
                showAlert(Alert.AlertType.ERROR, "File Not Found", "The file " + fileName + " does not exist.");
                return;
            }
            // create a temporary list for the loadedData
            ObservableList<Student> loadedStudents = FXCollections.observableArrayList();

            try {
                // load the data from the file
                studentManager.loadFromFile(fileName, loadedStudents);

                // debug: print loaded students
                for (Student student : loadedStudents) {
                    System.out.println("Loaded student: " + student.getName() + ", ID: " + student.getId() + ", GPA: " + student.getGpa());
                }

                // Update the TableView to display the updated list
                studentDisplayTable.setItems(loadedStudents); // clear the existing list
                studentDisplayTable.refresh();

                showAlert(Alert.AlertType.INFORMATION, "Load Data", "Data loaded successfully from " + fileName);
            } catch (Exception e) {
                showAlert(Alert.AlertType.ERROR, "Load Error", "Failed to load data");
            }
        });
    }

    /**
     * Exits the system when the exit button is clicked
     */
    public void exit() {
        System.exit(0);
    }

    /**
     * Allows alerts to be shown in the GUI with specific formatting
     * @param alertType
     * @param title
     * @param message
     */
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
