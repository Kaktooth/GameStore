package com.store.gamestore.controller.rest;

import com.store.gamestore.model.dto.UserGameDTO;
import com.store.gamestore.model.util.UserGameMapper;
import com.store.gamestore.persistence.entity.GameFile;
import com.store.gamestore.persistence.entity.UserGame;
import com.store.gamestore.service.game.collection.UserGamesService;
import com.store.gamestore.service.game.file.GameFileService;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class RestUserGamesController {

  private final UserGamesService userGamesService;
  private final GameFileService gameFileService;

  private final UserGameMapper userGameMapper;

  @GetMapping("/{userId}/games")
  public List<UserGameDTO> getUserGames(@PathVariable String userId) {

    ArrayList<UserGameDTO> userGameDTOs = new ArrayList<>();
    List<UserGame> userGames = userGamesService.findAllByUserId(UUID.fromString(userId));
    userGameMapper.sourceToDestination(userGames);
    return userGameDTOs;
  }

  @GetMapping("/download/{gameId}")
  public ResponseEntity<byte[]> getGameFiles(@PathVariable String gameId)
      throws IOException, SQLException {
    GameFile gameFile = gameFileService.getLatestFileVersionByGameId(UUID.fromString(gameId));
    byte[] bytes = gameFile.getFile().getBinaryStream().readAllBytes();

    HttpHeaders headers = new HttpHeaders();
    headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + gameFile.getName());

    return
        ResponseEntity.ok()
            .headers(headers)
            .contentLength(bytes.length)
            .contentType(MediaType.APPLICATION_OCTET_STREAM)
            .body(bytes);
  }
}
