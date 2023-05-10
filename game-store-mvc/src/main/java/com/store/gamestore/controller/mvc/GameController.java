package com.store.gamestore.controller.mvc;

import com.store.gamestore.common.AppConstraints;
import com.store.gamestore.common.Pagination;
import com.store.gamestore.common.mapper.GameRecommendationMapper;
import com.store.gamestore.common.mapper.SystemRequirementsMapper;
import com.store.gamestore.common.message.sender.UserInteractionSender;
import com.store.gamestore.model.util.UserHolder;
import com.store.gamestore.persistence.entity.FavoriteGame;
import com.store.gamestore.persistence.entity.Game;
import com.store.gamestore.persistence.entity.GamePictureType;
import com.store.gamestore.persistence.entity.InteractionType;
import com.store.gamestore.service.CommonService;
import com.store.gamestore.service.game.collection.UserGamesService;
import com.store.gamestore.service.game.favorite.FavoriteGameService;
import com.store.gamestore.service.game.pictures.GamePictureService;
import com.store.gamestore.service.game.profile.GameProfileService;
import com.store.gamestore.service.game.uploaded.UploadedGameService;
import com.store.gamestore.service.recommendation.GameRecommendationService;
import com.store.gamestore.service.requirements.SystemRequirementsService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@Controller
@RequestMapping("/game/{gameId}")
@RequiredArgsConstructor
public class GameController {

  private final UserHolder userHolder;
  private final UserInteractionSender userInteractionSender;
  private final UserGamesService userGamesService;
  private final UploadedGameService uploadedGameService;
  private final GameProfileService gameProfileService;
  private final GamePictureService gameImageService;
  private final CommonService<Game, UUID> gameService;
  private final FavoriteGameService favoriteGameService;
  private final SystemRequirementsService requirementsService;
  private final SystemRequirementsMapper systemRequirementsMapper;
  private final GameRecommendationMapper gameRecommendationMapper;
  private final GameRecommendationService gameRecommendationService;

  @PostMapping("/add-favorite")
  public String addToFavorites(@PathVariable UUID gameId,
      @RequestParam(value = "recommender", required = false) String recommender,
      Model model, RedirectAttributes redirectAttributes) {

    var game = gameService.get(gameId);
    var user = userHolder.getAuthenticated();
    var favoriteGame = new FavoriteGame(user.getId(), game);
    favoriteGameService.save(favoriteGame);

    if (recommender != null) {
      userInteractionSender.send(InteractionType.FAVORITE, user.getId(), gameId, true, recommender);
    } else {
      userInteractionSender.send(InteractionType.FAVORITE, user.getId(), gameId, false, "");
    }

    return getGamePage(gameId, recommender, model, redirectAttributes);
  }

  @PostMapping("/remove-favorite")
  public String deleteFromFavorites(@PathVariable UUID gameId,
      @RequestParam(value = "recommender", required = false) String recommender,
      Model model, RedirectAttributes redirectAttributes) {

    var user = userHolder.getAuthenticated();
    favoriteGameService.deleteByGameIdAndUserId(gameId, user.getId());
    userInteractionSender.sendRemoval(InteractionType.FAVORITE, user.getId(), gameId);

    return getGamePage(gameId, recommender, model, redirectAttributes);
  }

  @GetMapping
  public String getGamePage(@PathVariable UUID gameId,
      @RequestParam(value = "recommender", required = false) String recommender,
      Model model, RedirectAttributes redirectAttributes) {

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

      if (recommender != null) {
        userInteractionSender.send(InteractionType.VISITED, user.getId(), gameId, true,
            recommender);
      } else {
        userInteractionSender.send(InteractionType.VISITED, user.getId(), gameId, false, "");
      }
    }

    var similarGamesRecommendations = gameRecommendationService.getRecommendations(gameId);
    var similarGamesDTO = gameRecommendationMapper.sourceToDestination(similarGamesRecommendations);
    var pagination = new Pagination<>(similarGamesDTO);
    var pageLength = pagination.getPageCount(AppConstraints.Pagination.PAGE_SIZE);
    var similarGamesMap = pagination.toMap(AppConstraints.Pagination.PAGE_SIZE, pageLength);
    model.addAttribute("similarGamesMap", similarGamesMap);

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
    model.addAttribute("recommender", recommender);
    redirectAttributes.addAttribute("recommender", recommender);
    redirectAttributes.addFlashAttribute("recommender", recommender);

    return "game";
  }
}
