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
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GamesService {

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
      log.error(exception.getMessage());
      return Collections.emptyList();
    }
  }

  public synchronized long downloadGame(Game game, Tile tile) throws IOException {
    final var installersPath = "C:\\Users\\Xiaomi\\GameStore\\installers";

    final var downloadGamesUrl = new URL(
        HTTP + "://" + HOST + ":" + PORT + "/api/download/" + game.getId());
    final var conn = (HttpURLConnection) downloadGamesUrl.openConnection();
    log.info("downloading... url: {}", downloadGamesUrl);
    try (InputStream is = conn.getInputStream()) {
      String fileName = conn.getHeaderField("Content-Disposition")
          .replace("attachment; filename=", "");
      Path setupPath = Path.of(installersPath + "\\" + fileName);
      log.info("setup path: {}", setupPath);
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
      log.info("game downloaded");
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
