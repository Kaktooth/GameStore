package com.store.gamestore.controller.rest;

import com.store.gamestore.model.entity.GameBlob;
import com.store.gamestore.model.entity.GameImage;
import com.store.gamestore.model.entity.Image;
import com.store.gamestore.model.entity.PictureType;
import com.store.gamestore.model.entity.UserGame;
import com.store.gamestore.model.entity.UserGameDTO;
import com.store.gamestore.model.util.GamePicturesUtil;
import com.store.gamestore.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class RestUserGamesController {

    private final CommonService<GameImage, UUID> gameImageService;
    private final CommonService<UserGame, UUID> userGamesRepository;
    private final CommonService<GameBlob, UUID> gameFileRepository;

    @Autowired
    public RestUserGamesController(CommonService<GameImage, UUID> gameImageService,
                                   CommonService<UserGame, UUID> userGamesRepository,
                                   CommonService<GameBlob, UUID> gameFileRepository) {
        this.gameImageService = gameImageService;
        this.userGamesRepository = userGamesRepository;
        this.gameFileRepository = gameFileRepository;
    }

    @GetMapping("/{userId}/games")
    public List<UserGameDTO> getUserGames(@PathVariable String userId) {

        ArrayList<UserGameDTO> userGameDTOs = new ArrayList<>();
        List<UserGame> userGames = userGamesRepository.getAll(UUID.fromString(userId));
        for (var game : userGames) {
            List<GameImage> images = gameImageService.getAll(game.getGame().getId());
            Image image = GamePicturesUtil.getGamePicture(images, PictureType.GAMEPAGE);
            userGameDTOs.add(new UserGameDTO(game, image));
        }
        return userGameDTOs;
    }

    @GetMapping("/download/{gameId}")
    public ResponseEntity<byte[]> getGameFiles(@PathVariable String gameId) throws IOException, SQLException {
        GameBlob blob = gameFileRepository.get(UUID.fromString(gameId));
        byte[] bytes = blob.getBlob().getBinaryStream().readAllBytes();


        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=net5.0.zip");

        return
            ResponseEntity.ok()
                .headers(headers)
                .contentLength(bytes.length)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(bytes);
    }
}
