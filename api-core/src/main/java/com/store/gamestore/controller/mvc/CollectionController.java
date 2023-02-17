package com.store.gamestore.controller.mvc;

import com.store.gamestore.model.util.UserHolder;
import com.store.gamestore.persistence.entity.GamePictureType;
import com.store.gamestore.service.game.collection.UserGamesService;
import com.store.gamestore.service.game.pictures.GamePictureService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/collection")
@RequiredArgsConstructor
public class CollectionController {

  private final UserHolder userHolder;
  private final GamePictureService gameImageService;
  private final UserGamesService userGamesService;

  @GetMapping
  public String getCollectionPage(Model model) {
    var user = userHolder.getAuthenticated();
    var gameCollection = userGamesService.findAllByUserId(user.getId());
    model.addAttribute("collection", gameCollection);
    model.addAttribute("user", user);

    var gameIds = gameCollection
        .stream()
        .map(userGame -> userGame.getGame().getId())
        .toList();
    var gameCollectionImages = gameImageService.findGamePictureByGameIdsAndPictureTypeId(gameIds,
        GamePictureType.COLLECTION.ordinal());
    model.addAttribute("gameCollectionImages", gameCollectionImages);

    return "collection";
  }
}
