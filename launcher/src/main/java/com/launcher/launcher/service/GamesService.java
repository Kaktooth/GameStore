package com.launcher.launcher.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;
import com.launcher.launcher.model.entity.Game;
import com.launcher.launcher.model.entity.UserGameDTO;

import java.io.IOException;
import java.io.InputStream;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

public class GamesService {
    private final String filePath = "C:\\Users\\Xiaomi\\Game Store\\games";
    private final String protocol = "http";
    private final String host = "localhost";
    private final int port = 8082;

    public List<UserGameDTO> getGames(String userId) throws IOException {
        CookieHandler.setDefault(new CookieManager(null, CookiePolicy.ACCEPT_ALL));
        URL u = new URL(protocol + "://" + host + ":" + port + "/api/" + userId
            + "/games");
        HttpURLConnection conn = (HttpURLConnection) u.openConnection();
        String nullMultipartObject = " \"multipartFile\" " + ":null,";

        try (InputStream is = conn.getInputStream()) {
            String json = new String(is.readAllBytes());
            json = json.replace(nullMultipartObject, "");

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JSR310Module());
            return objectMapper.readValue(json, new TypeReference<>() {
            });
        } catch (Exception exception) {
            exception.printStackTrace();
            return Collections.emptyList();
        }
    }

    public void downloadGame(Game game) throws IOException {
        System.out.println("downloading......");
        URL u = new URL(protocol + "://" + host + ":" + port + "/api/download/" + game.id);
        System.out.println(u);
        HttpURLConnection conn = (HttpURLConnection) u.openConnection();
        Path path = Path.of(filePath);
        Path zipPath = Path.of(filePath + "\\" + "setup" + ".msi");
        try (InputStream is = conn.getInputStream()) {

        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
