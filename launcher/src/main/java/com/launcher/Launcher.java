package com.launcher;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;

public class Launcher extends Application {

  @Override
  public void start(Stage stage) throws IOException {
    var resourceUrl = getClass().getResource("/login-view.fxml");
    FXMLLoader fxmlLoader = new FXMLLoader(resourceUrl);
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