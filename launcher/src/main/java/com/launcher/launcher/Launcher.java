package com.launcher.launcher;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;

import java.io.IOException;

public class Launcher extends javafx.application.Application {

    @Override
    public void start(Stage stage) throws IOException {
//        FXMLLoader fxmlLoader = new FXMLLoader(Launcher.class.getResource("view.fxml"));
//        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
//        stage.setTitle("Hello!");
//        stage.setScene(scene);
//        stage.show();

//        Panel panel = new Panel("This is the title");
//        panel.getStyleClass().add("panel-primary");
//
//        BorderPane content = new BorderPane();
//        content.setPadding(new Insets(20));
//        Button button = new Button("Hello BootstrapFX");
//        button.getStyleClass().setAll("btn", "btn-danger");
//        content.setCenter(button);
//        panel.setBody(content);

        FXMLLoader fxmlLoader = new FXMLLoader(Launcher.class.getResource("loginview.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());

        stage.setTitle("Login page");
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}