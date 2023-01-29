package com.launcher.controller;

import com.launcher.Launcher;
import com.launcher.service.AuthenticationService;
import com.launcher.service.GamesService;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class AuthenticationController {

  Logger log = LoggerFactory.getLogger(GamesService.class);
  private final AuthenticationService authenticationService = new AuthenticationService();
  @FXML
  private TextField username;
  @FXML
  private TextField password;
  @FXML
  private Button loginButton;

  @FXML
  protected void authenticateUser() throws IOException {
    final var user = authenticationService.authenticate(username.getText(), password.getText());

    if (user != null) {
      log.info("User with name {} logged successfully", user.getPublicUsername());
      var stage = new Stage();
      stage.setUserData(user);
      var fxmlLoader = new FXMLLoader(Launcher.class.getResource("/view.fxml"));
      var scene = new Scene(fxmlLoader.load(), 1000, 800);
      scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
      GamesController gamesController = fxmlLoader.getController();
      gamesController.initData(user);

      stage.setTitle("Game installer");
      stage.setScene(scene);
      stage.sizeToScene();
      stage.show();
    }

    var stage = (Stage) loginButton.getScene().getWindow();
    log.info("games window loading");
    stage.close();
  }
}