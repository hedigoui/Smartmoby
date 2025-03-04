module module_name {
    requires java.sql;
    requires javafx.base;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires AnimateFX;
    requires kernel;
    requires layout;

    opens com.esprit.tests to javafx.fxml, javafx.graphics;

    exports com.esprit.controllers;
    opens com.esprit.controllers;
    exports com.esprit.models;
}