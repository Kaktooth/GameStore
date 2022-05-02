package com.launcher.launcher.controller;

import com.launcher.launcher.Launcher;
import com.launcher.launcher.model.entity.User;
import com.launcher.launcher.service.AuthenticationService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class AuthenticationController {

    private static Logger log = LoggerFactory.getLogger(AuthenticationController.class);
    private final AuthenticationService authenticationService;

    @FXML
    private TextField username;
    @FXML
    private TextField password;
    @FXML
    private Button loginButton;

    public AuthenticationController() {
        this.authenticationService = new AuthenticationService();
    }

    @FXML
    protected void authenticateUser() throws IOException {
        User user = authenticationService.authenticate(username.getText(), password.getText());

        if (user != null) {
            log.info("User with name {} logged successfully", user.getPublicUsername());
            Stage stage = new Stage();
            stage.setUserData(user);
            FXMLLoader fxmlLoader = new FXMLLoader(Launcher.class.getResource("view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1000, 800);
            scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
            GamesController gamesController = fxmlLoader.getController();
            gamesController.initData(user);

            stage.setTitle("Game installer");
            stage.setScene(scene);
            stage.sizeToScene();
            stage.show();
        }

        Stage stage = (Stage) loginButton.getScene().getWindow();
        log.info("games window loaded");
        stage.close();
    }
}