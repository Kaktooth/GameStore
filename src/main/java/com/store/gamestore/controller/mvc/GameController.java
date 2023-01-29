package com.store.gamestore.controller.mvc;

import com.store.gamestore.model.util.SystemRequirementsMapper;
import com.store.gamestore.model.util.UserHolder;
import com.store.gamestore.persistence.entity.FavoriteGame;
import com.store.gamestore.persistence.entity.Game;
import com.store.gamestore.persistence.entity.GamePictureType;
import com.store.gamestore.service.CommonService;
import com.store.gamestore.service.game.collection.UserGamesService;
import com.store.gamestore.service.game.favorite.FavoriteGameService;
import com.store.gamestore.service.game.pictures.GamePictureService;
import com.store.gamestore.service.game.profile.GameProfileService;
import com.store.gamestore.service.game.uploaded.UploadedGameService;
import com.store.gamestore.service.requirements.SystemRequirementsService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/game/{gameId}")
@RequiredArgsConstructor
public class GameController {

  private final UserHolder userHolder;
  private final UserGamesService userGamesService;
  private final UploadedGameService uploadedGameService;
  private final GameProfileService gameProfileService;
  private final GamePictureService gameImageService;
  private final CommonService<Game, UUID> gameService;
  private final FavoriteGameService favoriteGameService;
  private final SystemRequirementsService requirementsService;
  private final SystemRequirementsMapper systemRequirementsMapper;

  @PostMapping("/add-favorite")
  public String addToFavorites(@PathVariable UUID gameId, Model model) {

    var game = gameService.get(gameId);
    var user = userHolder.getAuthenticated();
    var favoriteGame = new FavoriteGame(user.getId(), game);
    favoriteGameService.save(favoriteGame);
    getGamePage(gameId, model);

    return "game";
  }

  @PostMapping("/remove-favorite")
  public String deleteFromFavorites(@PathVariable UUID gameId, Model model) {
    var user = userHolder.getAuthenticated();
    favoriteGameService.deleteByGameIdAndUserId(gameId, user.getId());
    getGamePage(gameId, model);

    return "game";
  }

  @GetMapping
  public String getGamePage(@PathVariable UUID gameId, Model model) {

    var user = userHolder.getAuthenticated();
    model.addAttribute("user", user);

    var game = gameService.get(gameId);
    var gameProfile = gameProfileService.findGameProfileByGameId(
        game.getId());
    var systemRequirements = requirementsService.findByGameProfileId(
        gameProfile.getId());
    var systemRequirementsDTO = systemRequirementsMapper
        .sourceToDestination(systemRequirements);

    Boolean favorite = null;
    Boolean purchased = null;
    var canBePurchased = false;
    if (user != null) {
      favorite = favoriteGameService.existsByGameIdAndUserId(gameId, user.getId());
      purchased = userGamesService.existsByGameIdAndUserId(gameId, user.getId());
      canBePurchased = !uploadedGameService.findByGameId(gameId).getUserId().equals(user.getId());
    }

    var gamePageImage = gameImageService.findGamePictureByGameIdAndPictureTypeId(gameId,
        GamePictureType.GAME_PAGE.ordinal());
    model.addAttribute("gamePagePicture", gamePageImage);

    final var gameplayPictures = gameImageService.findGameplayPicturesByGameId(gameId);
    model.addAttribute("gameplayPictures", gameplayPictures);

    model.addAttribute("favorite", favorite);
    model.addAttribute("purchased", purchased);
    model.addAttribute("canBePurchased", canBePurchased);
    model.addAttribute("gameProfile", gameProfile);
    model.addAttribute("game", game);
    model.addAttribute("requirements", systemRequirementsDTO);

    return "game";
  }
}
