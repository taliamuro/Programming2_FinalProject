// -------------------------------------------------------
// Final Project
// Written by: Talia Muro
// For “Programming 2” Section 1 – Fall 2024
// --------------------------------------------------------

package org.talia.javafxtest;

public abstract class Person {
    private String name;

    public Person() {
    }

    public Person(String name) {
        this.name = name;
    }

    /**
     * To be overwritten in the Student class
     * @return a formatted String
     */
    public abstract String getDescription();

    public String getName() {
        return name;
    }
}
