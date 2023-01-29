module com.launcher {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.datatype.jsr310;
    requires org.slf4j;
    requires org.slf4j.simple;
    requires java.logging;

    opens com.launcher to javafx.fxml;
    exports com.launcher;
    exports com.launcher.service;
    exports com.launcher.controller;
    opens com.launcher.controller to javafx.fxml;
    exports com.launcher.model.entity;
}