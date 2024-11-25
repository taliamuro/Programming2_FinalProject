// -------------------------------------------------------
// Final Project
// Written by: Talia Muro
// For “Programming 2” Section 1 – Fall 2024
// --------------------------------------------------------

package org.talia.javafxtest;

import javafx.collections.ObservableList;

import java.io.*;
import java.util.ArrayList;

public class StudentManager implements FileHandler {
    private ObservableList<Student> students;

    public StudentManager(ObservableList<Student> students) {
        this.students = students;
    }

    /**
     *
     * @param student object of Student
     */
    public void addStudent(Student student) {
        students.add(student);
    }

    public void removeStudent(int id) throws StudentNotFoundException {
        Student student = searchStudentById(id);
        if (student != null) {
            students.remove(student);
        } else {
            throw new StudentNotFoundException("Student with ID : " + id + " not found.");
        }
    }

    /**
     * Returns the student using the wrapper method.
     * @param id
     * @return Object of Student which matches the input id
     * @throws StudentNotFoundException if no student with the input id exists
     */
    public Student searchStudentById(int id) throws StudentNotFoundException {
        return searchStudentByIdWrapper(id, 0);
    }

    /**
     * Recursive wrapper method to retrieve the student id at each index to see if it matches the input.
     * @param id input id
     * @param index input index
     * @return student with matching id
     * @throws StudentNotFoundException if no student with the input id is found
     */
    private Student searchStudentByIdWrapper(int id, int index) throws StudentNotFoundException {
        if (index >= students.size()) {
            throw new StudentNotFoundException("Student with ID " + id + " not found.");
        }

        Student student = students.get(index);
        if (student.getId() == id) {
            return student;
        }

        return searchStudentByIdWrapper(id, index + 1);

    }

    /**
     * Writes student(s) to a text file
     * @param fileName input fileName to write to
     * @param students input ObservableList with student object to write to the file
     */
    public void saveToFile(String fileName, ObservableList<Student> students) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (Student student : students) {
                writer.write(student.getName() + "," + student.getId() + "," + student.getGpa());
                writer.newLine();
            }
        }
        catch (IOException e) {
            System.out.println("Error saving to file: " + e.getMessage());
        }
    }

    /**
     * Adds students from a text file to an Observable list
     * @param fileName the input file name
     * @param tempStudents the empty input ObservableList
     * @throws IOException
     */
    public void loadFromFile(String fileName, ObservableList<Student> tempStudents) throws IOException {
        File file = new File(fileName);
        if (!file.exists()) {
            throw new IOException("File not found");
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String name = parts[0].trim();
                    int id = Integer.parseInt(parts[1].trim());
                    double gpa = Double.parseDouble(parts[2].trim());
                    tempStudents.add(new Student(name, id, gpa));
                }
            }
        }
    }

    /**
     * Formats the output of the students ObservableList
     */
    public void displayStudents() {
        for (Student student : students) {
            System.out.println(student.getDescription());
        }
    }

    public ObservableList<Student> getStudents() {
        return students;
    }
}
