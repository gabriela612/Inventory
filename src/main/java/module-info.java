module inventory {
    requires javafx.base;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    opens inventory             to javafx.base, javafx.controls, javafx.fxml, javafx.graphics;
    opens inventory.model       to javafx.base, javafx.controls, javafx.fxml, javafx.graphics;
    opens inventory.repository  to javafx.base, javafx.controls, javafx.fxml, javafx.graphics;
    opens inventory.service     to javafx.base, javafx.controls, javafx.fxml, javafx.graphics;
    opens inventory.controller  to javafx.base, javafx.controls, javafx.fxml, javafx.graphics;

    exports inventory;
    exports inventory.model;
    exports inventory.repository;
    exports inventory.service;
    exports inventory.controller;
}