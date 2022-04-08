package com.store.gamestore.controller.rest;

import com.store.gamestore.model.GameImage;
import com.store.gamestore.model.Image;
import com.store.gamestore.model.PictureType;
import com.store.gamestore.model.UserGame;
import com.store.gamestore.model.UserGameDTO;
import com.store.gamestore.service.CommonService;
import com.store.gamestore.util.GamePicturesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class RestUserGamesController {

    private final CommonService<GameImage, UUID> gameImageService;
    private final CommonService<UserGame, UUID> userGamesRepository;

    @Autowired
    public RestUserGamesController(CommonService<GameImage, UUID> gameImageService,
                                   CommonService<UserGame, UUID> userGamesRepository) {
        this.gameImageService = gameImageService;
        this.userGamesRepository = userGamesRepository;
    }

    @GetMapping("/{userId}/games")
    public List<UserGameDTO> getUserGames(@PathVariable String userId) {

        List<UserGameDTO> userGameDTOs = new ArrayList<>();
        List<UserGame> userGames = userGamesRepository.getAll(UUID.fromString(userId));
        for (var game : userGames) {
            List<GameImage> images = gameImageService.getAll(game.getGame().getId());
            Image image = GamePicturesUtil.getGamePicture(images, PictureType.COLLECTION);
            userGameDTOs.add(new UserGameDTO(game, image));
        }

        return userGameDTOs;
    }

}
