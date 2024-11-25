// -------------------------------------------------------
// Final Project
// Written by: Talia Muro
// For “Programming 2” Section 1 – Fall 2024
// --------------------------------------------------------

package org.talia.javafxtest;

import java.util.Scanner;

public class Student extends Person {
    private int id;
    private double gpa;

    public Student(String name, int id, double gpa) {
        super(name);
        this.id = id;
        this.gpa = gpa;
    }

    /**
     * Detailed description of the student
     * @return a String formatted with name, ID, and GPA
     */
    public String getDescription() {
        return "\nStudent Info:\nName: " + getName() + "\nID: " + id + "\nGPA: " + gpa;
    }

    public int getId() {
        return id;
    }

    public double getGpa() {
        return gpa;
    }
}
