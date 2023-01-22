package com.launcher.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.launcher.model.entity.Game;
import com.launcher.model.entity.UserGameDTO;
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
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Collections;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GamesService {

  Logger log = LoggerFactory.getLogger(GamesService.class);
  private static final String HTTP = "http";
  private static final Integer PORT = 8082;
  private static final String HOST = "localhost";

  public List<UserGameDTO> getGames(String userId) throws IOException {

    CookieHandler.setDefault(new CookieManager(null, CookiePolicy.ACCEPT_ALL));

    final var gamesUrl = new URL(HTTP + "://" + HOST + ":" + PORT + "/api/" + userId + "/games");
    final var conn = (HttpURLConnection) gamesUrl.openConnection();
    final var nullMultipartObject = " \"multipartFile\" " + ":null,";

    try (InputStream is = conn.getInputStream()) {
      var json = new String(is.readAllBytes());
      json = json.replace(nullMultipartObject, "");

      var objectMapper = new ObjectMapper();
      return objectMapper.readValue(json, new TypeReference<>() {
      });
    } catch (Exception exception) {
      return Collections.emptyList();
    }
  }

  public synchronized long downloadGame(Game game, Tile tile) throws IOException {
    final var installersPath = System.getenv("APPDATA") + "\\GameStore\\installers";
    Path installersDir = Paths.get(installersPath);
    if (!Files.exists(installersDir)) {
      Files.createDirectories(installersDir);
    }

    final var downloadGamesUrl = new URL(
        HTTP + "://" + HOST + ":" + PORT + "/api/download/" + game.getId());
    final var conn = (HttpURLConnection) downloadGamesUrl.openConnection();
    try (InputStream is = conn.getInputStream()) {
      String fileName = conn.getHeaderField("Content-Disposition")
          .replace("attachment; filename=", "");
      Path setupPath = Path.of(installersPath + "\\" + fileName);
      long writtenBytes = Files.copy(is, setupPath, StandardCopyOption.REPLACE_EXISTING);
      tile.setValue(writtenBytes);
      Process downloadProcess;
      if (setupPath.toString().contains(".msi")) {
        downloadProcess = Runtime.getRuntime().exec(String.format("msiexec /i %s", setupPath));
        downloadProcess.waitFor();
      } else {
        downloadProcess = Runtime.getRuntime().exec("cmd /c start cmd.exe /C\"" + setupPath + "\"");
        downloadProcess.waitFor();
      }
    } catch (InterruptedException exception) {
      log.error("InterruptedException Error message: {}", exception.getMessage());
      Thread.currentThread().interrupt();
    } catch (IOException exception) {
      log.error("IOException Error message: {}", exception.getMessage());
    }
    String contentLength = conn.getHeaderField("Content-Length");
    log.info("bytes downloaded: {}", contentLength);
    return Long.parseLong(contentLength);
  }
}
