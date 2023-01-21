package com.launcher.launcher.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;
import com.launcher.launcher.model.entity.User;
import javafx.scene.control.Alert;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.HttpURLConnection;
import java.net.URL;

@Slf4j
public class AuthenticationService {
    private static final String HTTP = "http";
    private static final String HOST = "localhost";
    private static final int PORT = 8082;


    public User authenticate(String username, String password) throws IOException {
        CookieHandler.setDefault(new CookieManager(null, CookiePolicy.ACCEPT_ALL));
        URL u = new URL(HTTP + "://" + HOST + ":" + PORT + "/api/auth" +
            "?username=" + username + "&password=" + password);
        HttpURLConnection conn = (HttpURLConnection) u.openConnection();

        try (InputStream is = conn.getInputStream()) {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(new String(is.readAllBytes()), User.class);
        } catch (Exception exception) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Authentication Error");
            alert.setContentText("Authentication Failed");
            alert.showAndWait();

            log.error("Error message: {}", exception.getMessage());
            return null;
        }
    }
}
