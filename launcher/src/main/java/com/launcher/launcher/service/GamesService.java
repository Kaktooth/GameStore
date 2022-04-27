package com.launcher.launcher.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;
import com.launcher.launcher.model.entity.Game;
import com.launcher.launcher.model.entity.UserGameDTO;
import eu.hansolo.tilesfx.Tile;

import java.io.IOException;
import java.io.InputStream;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Collections;
import java.util.List;

public class GamesService {

    private final String installersPath = "C:\\Users\\Xiaomi\\GameStore\\installers";
    private final String gamesPath = "C:\\Users\\Xiaomi\\GameStore\\games";
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

    public synchronized long downloadGame(Game game, Tile tile) throws IOException {
        System.out.println("downloading......");
        URL u = new URL(protocol + "://" + host + ":" + port + "/api/download/" + game.id);
        System.out.println(u);
        HttpURLConnection conn = (HttpURLConnection) u.openConnection();

        try (InputStream is = conn.getInputStream()) {
            String fileName = conn.getHeaderField("Content-Disposition").replace("attachment; filename=", "");
            Path setupPath = Path.of(installersPath + "\\" + fileName);
            System.out.println("setup path: " + setupPath);
            long writtenBytes = Files.copy(is, setupPath, StandardCopyOption.REPLACE_EXISTING);
            tile.setValue(writtenBytes);
            Process prc;
            if (setupPath.toString().contains(".msi")) {
                prc = Runtime.getRuntime().exec(String.format("msiexec /i %s", setupPath));
                prc.waitFor();
            } else {
                prc = Runtime.getRuntime().exec("cmd /c start cmd.exe /C\"" + setupPath + "\"");
                prc.waitFor();
            }
            System.out.println("game downloaded");

            Files.delete(setupPath);
            System.out.println("installer deleted");
        } catch (InterruptedException | IOException exception) {
            exception.printStackTrace();
        }
        String contentLength = conn.getHeaderField("Content-Length");
        System.out.println("contentLength: " + contentLength);
        return Long.parseLong(contentLength);
    }
}
