module com.launcher.launcher {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires lombok;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.datatype.jsr310;

    opens com.launcher.launcher to javafx.fxml;
    exports com.launcher.launcher;
    exports com.launcher.launcher.service;
    exports com.launcher.launcher.controller;
    opens com.launcher.launcher.controller to javafx.fxml;
    exports com.launcher.launcher.model.entity;
}