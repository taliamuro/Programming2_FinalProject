package org.talia.javafxtest;

import javafx.collections.ObservableList;

import java.io.IOException;
import java.util.ArrayList;

public interface FileHandler {
    void saveToFile(String fileName, ObservableList<Student> students);
    void loadFromFile(String fileName, ObservableList<Student> tempStudents) throws IOException;
}
