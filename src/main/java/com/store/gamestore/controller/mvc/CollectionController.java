package com.store.gamestore.controller.mvc;

import com.store.gamestore.persistence.entity.GamePicture;
import com.store.gamestore.persistence.entity.GamePictureType;
import com.store.gamestore.persistence.entity.User;
import com.store.gamestore.persistence.entity.UserGame;
import com.store.gamestore.service.game.collection.UserGamesService;
import com.store.gamestore.service.game.pictures.GamePictureService;
import com.store.gamestore.service.user.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/collection")
@RequiredArgsConstructor
public class CollectionController {

  private final UserService userService;
  private final GamePictureService gameImageService;
  private final UserGamesService userGamesService;

  @GetMapping
  public String getCollectionPage(Model model) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String name = authentication.getName();
    User user = userService.findUserByUsername(name);

    List<UserGame> gameCollection = userGamesService.findAllByUserId(user.getId());
    model.addAttribute("collection", gameCollection);
    model.addAttribute("user", user);

    final var gameIds = gameCollection
        .stream()
        .map(userGame -> userGame.getGame().getId())
        .toList();
    List<GamePicture> gameCollectionImages = gameImageService
        .findGamePictureByGameIdsAndPictureTypeId(gameIds, GamePictureType.COLLECTION.ordinal());
    model.addAttribute("gameCollectionImages", gameCollectionImages);

    return "collection";
  }
}
