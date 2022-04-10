module com.launcher.launcher {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.google.gson;
    requires lombok;

    opens com.launcher.launcher to javafx.fxml;
    exports com.launcher.launcher;
    exports com.launcher.launcher.model.entity;
    exports com.launcher.launcher.model.service;
}