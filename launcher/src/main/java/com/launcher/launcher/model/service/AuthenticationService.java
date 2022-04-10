package com.launcher.launcher.model.service;

import com.google.gson.Gson;
import com.launcher.launcher.model.entity.User;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class AuthenticationService {
    private final String protocol = "http://";
    private final String host = "localhost";
    private final int port = 8082;

    public void authenticate(String username, String password) throws IOException {
        Gson gsonUtil = new Gson();

        URL u = new URL(protocol + host + ":" + port + "/api/auth" +
            "?username=" + username + "&password=" + password);
        HttpURLConnection conn = (HttpURLConnection) u.openConnection();

        try (InputStream is = conn.getInputStream()) {
            User user = gsonUtil.fromJson(new String(is.readAllBytes()), User.class);
            System.out.println(user.username);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
