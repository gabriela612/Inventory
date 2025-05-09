module inventory {
    requires javafx.graphics;
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.base;

    opens inventory             to javafx.base, javafx.fxml, javafx.controls, org.junit.platform.commons;
    opens inventory.model       to javafx.base, javafx.fxml, javafx.controls, org.junit.platform.commons;
    opens inventory.repository  to javafx.base, javafx.fxml, javafx.controls, org.junit.platform.commons;
    opens inventory.service     to javafx.base, javafx.fxml, javafx.controls, org.junit.platform.commons;
    opens inventory.controller  to javafx.base, javafx.fxml, javafx.controls, org.junit.platform.commons;

    exports inventory;
    exports inventory.model;
    exports inventory.repository;
    exports inventory.service;
    exports inventory.controller;
}