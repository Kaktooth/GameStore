package com.store.gamestore.controller.mvc;

import com.store.gamestore.model.dto.SystemRequirementsDTO;
import com.store.gamestore.model.util.SystemRequirementsMapper;
import com.store.gamestore.persistence.entity.FavoriteGame;
import com.store.gamestore.persistence.entity.Game;
import com.store.gamestore.persistence.entity.GamePicture;
import com.store.gamestore.persistence.entity.GamePictureType;
import com.store.gamestore.persistence.entity.GameProfile;
import com.store.gamestore.persistence.entity.SystemRequirements;
import com.store.gamestore.persistence.entity.User;
import com.store.gamestore.persistence.entity.UserGame;
import com.store.gamestore.service.CommonService;
import com.store.gamestore.service.game.collection.UserGamesService;
import com.store.gamestore.service.game.favorite.FavoriteGameService;
import com.store.gamestore.service.game.pictures.GamePictureService;
import com.store.gamestore.service.game.profile.GameProfileService;
import com.store.gamestore.service.requirements.SystemRequirementsService;
import com.store.gamestore.service.user.UserService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

  private final UserService userService;
  private final UserGamesService userGamesService;
  private final GameProfileService gameProfileService;
  private final GamePictureService gameImageService;
  private final CommonService<Game, UUID> gameService;
  private final FavoriteGameService favoriteGameService;
  private final SystemRequirementsService requirementsService;
  private final SystemRequirementsMapper systemRequirementsMapper;

  @PostMapping("/add-favorite")
  public String addToFavorites(@PathVariable UUID gameId, Model model) {

    Game game = gameService.get(gameId);
    User user = getUser();
    FavoriteGame favoriteGame = new FavoriteGame(user.getId(), game);
    favoriteGameService.save(favoriteGame);
    getGamePage(gameId, model);

    return "game";
  }

  @PostMapping("/remove-favorite")
  public String deleteFromFavorites(@PathVariable UUID gameId, Model model) {
    User user = getUser();
    favoriteGameService.deleteByGameIdAndUserId(gameId, user.getId());
    getGamePage(gameId, model);

    return "game";
  }

  @GetMapping
  public String getGamePage(@PathVariable UUID gameId, Model model) {

    User user = getUser();
    model.addAttribute("user", user);

    Game game = gameService.get(gameId);
    GameProfile gameProfile = gameProfileService.findGameProfileByGameId(
        game.getId());
    SystemRequirements systemRequirements = requirementsService.findByGameProfileId(
        gameProfile.getId());
    SystemRequirementsDTO systemRequirementsDTO = systemRequirementsMapper
        .sourceToDestination(systemRequirements);

    List<FavoriteGame> favoriteGameList = favoriteGameService.findAllByUserId(user.getId());

    boolean favorite = false;
    for (FavoriteGame favoriteGame : favoriteGameList) {
      if (favoriteGame.getGame().getId().equals(gameId)) {
        favorite = true;
        break;
      }
    }

    GamePicture gamePageImage = gameImageService.findGamePictureByGameIdAndPictureTypeId(gameId,
        GamePictureType.GAME_PAGE.ordinal());
    model.addAttribute("gamePagePicture", gamePageImage);

    final var gameplayPictures = gameImageService.findGameplayPicturesByGameId(gameId);
    model.addAttribute("gameplayPictures", gameplayPictures);

    List<UserGame> userGames = userGamesService.findAllByUserId(user.getId());
    boolean purchased = false;
    for (UserGame userGame : userGames) {
      if (userGame.getGame().getId().equals(game.getId())) {
        purchased = true;
        break;
      }
    }
    model.addAttribute("favorite", favorite);
    model.addAttribute("purchased", purchased);
    model.addAttribute("gameProfile", gameProfile);
    model.addAttribute("game", game);
    model.addAttribute("requirements", systemRequirementsDTO);

    return "game";
  }

  private User getUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String name = authentication.getName();
    return userService.findUserByUsername(name);
  }
}
