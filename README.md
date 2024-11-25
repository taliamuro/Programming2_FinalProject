# Student Manager System

 ## Overview
This is a Java-based Student Manager System that allows users to manage student records effifiently through a 
graphical user interface (GUI). It provides functionalities to add, delete, search, and display students, as well
as save and load student records from a text file. Each student has a name, a unique ID, and a GPA.

## Person Class
The Person class is a base class that represents a general person with a single instance variable for the person's name
and an abstract method getDescription to be overridden in the Student class.

## Student Class
The Student class is derived from Person. It adds two new instance variables: ID (int), and GPA (double). It overrides
the abstract method getDescription from the Person class to properly format a student object.

## StudentManager Class
The StudentManager class has one instance variable students of type Student ObservableList to manage the students being
added and removed from the list. It has methods addStudent, removeStudent, saveToFile, loadFromFile, and 
searchStudentById, a recursive method that searches for a student based on their ID.

## FileHandler Interface
The FileHandler interface defines the methods saveToFile and loadFromFile to ensure that the StudentManager class
properly handles file operations.

## StudentManagerApplication Class
The StudentManagerApplication class works with the GUI to display and store the students ObservableList in the correct
way. It implements methods initialize, addStudent, searchById, searchByName, displayAllStudents, saveData, loadData,
exit, and showAlert.

## StudentManagerController Class
The StudentManagerController Class has one method start, that sets the stage for the GUI. From the main method in this
class, we can run the whole application in order for the user to use the GUI.

## FXML File
The FXML file that is derived from the Scene Builder application is used by the StudentApplication class to generate the
code for the GUI.
