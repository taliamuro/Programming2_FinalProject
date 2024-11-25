module org.talia.javafxtest {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens org.talia.javafxtest to javafx.fxml;
    exports org.talia.javafxtest;
}