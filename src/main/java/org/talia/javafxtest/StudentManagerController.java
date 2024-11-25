// -------------------------------------------------------
// Final Project
// Written by: Talia Muro
// For “Programming 2” Section 1 – Fall 2024
// --------------------------------------------------------

package org.talia.javafxtest;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class StudentManagerController extends Application {
    /**
     * Sets the Stage for the GUI application to run
     * @param primaryStage the input Stage object that is being set
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        // load the FXML file
        FXMLLoader fxmlLoader = new FXMLLoader(StudentManagerApplication.class.getResource("GuiMenu.fxml"));

        // set the scene with the FXML file
        Scene scene = new Scene(fxmlLoader.load(), 347, 581);

        // set up the primary stage
        primaryStage.setTitle("Student Manager");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
