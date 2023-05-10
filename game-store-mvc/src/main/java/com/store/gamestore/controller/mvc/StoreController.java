package com.store.gamestore.controller.mvc;

import com.store.gamestore.common.AppConstraints;
import com.store.gamestore.common.AppConstraints.Search;
import com.store.gamestore.common.Pagination;
import com.store.gamestore.common.mapper.GameMapper;
import com.store.gamestore.common.mapper.UserRecommendationMapper;
import com.store.gamestore.common.message.sender.UserInteractionSender;
import com.store.gamestore.config.UserFilteringSettingsConfig;
import com.store.gamestore.model.dto.GameDTO;
import com.store.gamestore.model.dto.UserRecommendationDTO;
import com.store.gamestore.model.util.UserHolder;
import com.store.gamestore.persistence.entity.Genre;
import com.store.gamestore.persistence.entity.InteractionType;
import com.store.gamestore.persistence.entity.StoreBanner;
import com.store.gamestore.persistence.entity.UserRecommendation;
import com.store.gamestore.service.CommonService;
import com.store.gamestore.service.enumeration.AbstractEnumerationService;
import com.store.gamestore.service.game.GameService;
import com.store.gamestore.service.recommendation.NonPersonalRecommendationService;
import com.store.gamestore.service.recommendation.TopicService;
import com.store.gamestore.service.recommendation.UserRecommendationService;
import com.store.gamestore.service.search.SearchService;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
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

@Slf4j
@Controller
@RequestMapping("/store")
@RequiredArgsConstructor
public class StoreController {

  private final NonPersonalRecommendationService nonPersonalRecommendationService;
  private final UserRecommendationService userRecommendationService;
  private final UserInteractionSender userInteractionSender;
  private final TopicService topicService;
  private final GameMapper gameMapper;
  private final GameService gameService;
  private final UserHolder userHolder;
  private final SearchService<GameDTO> gameSearchService;
  private final CommonService<StoreBanner, UUID> storeBannerService;
  private final UserRecommendationMapper userRecommendationMapper;
  private final AbstractEnumerationService<Genre, Integer> genreService;
  private final UserFilteringSettingsConfig userFilteringSettingsConfig;

  @GetMapping
  public String getMainStorePage(
      @RequestParam(value = "searchString", required = false) String searchString, Model model) {

    if (!model.containsAttribute("releaseDate")) {
      model.addAttribute("releaseDate", userFilteringSettingsConfig.getReleaseDate());
      model.addAttribute("minGamesPrice", userFilteringSettingsConfig.getMinGamesPrice());
      model.addAttribute("maxGamesPrice", userFilteringSettingsConfig.getMaxGamesPrice());
    }

    loadStorePage(searchString, model);
    var storeBanners = storeBannerService.getAll();
    model.addAttribute("bannerItems", storeBanners);

    var popularGames = nonPersonalRecommendationService.getPopularGames();
    var popularGamesDtoList = gameMapper.sourceToDestination(popularGames);
    var pagination = new Pagination<>(popularGamesDtoList);
    var pageLength = pagination.getPageCount(AppConstraints.Pagination.PAGE_SIZE);
    var popularGamesMap = pagination.toMap(AppConstraints.Pagination.PAGE_SIZE, pageLength);

    var mostPurchasedGames = nonPersonalRecommendationService.getMostPurchasedGames();
    var mostPurchasedGamesDtoList = gameMapper.sourceToDestination(mostPurchasedGames);
    pagination = new Pagination<>(mostPurchasedGamesDtoList);
    pageLength = pagination.getPageCount(AppConstraints.Pagination.PAGE_SIZE);
    var mostPurchasedGamesMap = pagination.toMap(AppConstraints.Pagination.PAGE_SIZE, pageLength);

    var favoriteGames = nonPersonalRecommendationService.getFavoriteGames();
    var favoriteGamesDtoList = gameMapper.sourceToDestination(favoriteGames);
    pagination = new Pagination<>(favoriteGamesDtoList);
    pageLength = pagination.getPageCount(AppConstraints.Pagination.PAGE_SIZE);
    var favoriteGamesMap = pagination.toMap(AppConstraints.Pagination.PAGE_SIZE, pageLength);

    model.addAttribute("popularGamesMap", popularGamesMap);
    model.addAttribute("bestSellerGamesMap", mostPurchasedGamesMap);
    model.addAttribute("mostFavoriteGamesMap", favoriteGamesMap);

    var authenticatedUser = userHolder.getAuthenticated();
    if (authenticatedUser != null) {
      model.addAttribute("user", authenticatedUser);
      var userBestRecommendations = userRecommendationService.getBestRecommendations();
      var userBestRecommendationsDTO = userRecommendationMapper.sourceToDestination(
          userBestRecommendations);
      var userRecommendedBestGames = userBestRecommendationsDTO.stream().distinct().toList();

      var topicVocabulary = topicService.getTopics();
      var categories = genreService.getAll();

      var userRecommendations = userRecommendationService.getRecommendations();
      userRecommendations = userRecommendations.stream()
          .distinct().toList();
      var userRecommendationsDTO = userRecommendationMapper.sourceToDestination(
          userRecommendations);

      var topicIds = userBestRecommendations.stream()
          .map(UserRecommendation::getTopicId)
          .distinct()
          .toList();

      var gamesRecommendedByTopic = new HashMap<Integer, List<UserRecommendationDTO>>();
      for (var topicId : topicIds) {
        var topicRecommendations = userRecommendationsDTO.stream()
            .filter(rec -> rec.getUserRecommendation().getTopicId().equals(topicId))
            .toList();
        gamesRecommendedByTopic.put(topicId, topicRecommendations);
      }

      var userRecommendationsByCategory = new HashMap<Genre, List<UserRecommendationDTO>>();
      for (var category : categories) {
        var recommendations = userRecommendationsDTO.stream()
            .filter(rec -> gameService.get(rec.getUserRecommendation().getGameId())
                .getGenres()
                .contains(category))
            .toList();
        userRecommendationsByCategory.put(category, recommendations);
      }

      model.addAttribute("userRecommendedBestGames", userRecommendedBestGames);
      model.addAttribute("gamesRecommendedByTopic", gamesRecommendedByTopic);
      model.addAttribute("topicVocabulary", topicVocabulary);
      model.addAttribute("categories", categories);
      model.addAttribute("userRecommendationsByCategory", userRecommendationsByCategory);
    }

    return "store";
  }

  private void loadStorePage(String searchString, Model model) {

    if (searchString == null || searchString.equals("")) {
      model.addAttribute("search", false);
    } else {
      var searchedGames = gameSearchService.searchGames(searchString, Search.RANGE);
      model.addAttribute("search", true);
      model.addAttribute("searchedGames", searchedGames);
    }
  }

  @PostMapping
  public String applyGamesFilter(@RequestParam LocalDate releaseDate,
      @RequestParam Double minGamesPrice,
      @RequestParam Double maxGamesPrice, Model model) {
    model.addAttribute("releaseDate", releaseDate);
    model.addAttribute("minGamesPrice", minGamesPrice);
    model.addAttribute("maxGamesPrice", maxGamesPrice);
    return "store";
  }

  @GetMapping("/ignore-game/{gameId}")
  public String ignoreGame(@PathVariable UUID gameId,
      @RequestParam(value = "searchString", required = false) String searchString, Model model) {
    var user = userHolder.getAuthenticated();
    userInteractionSender.send(InteractionType.IGNORED, user.getId(), gameId);
    getMainStorePage(searchString, model);
    return "store";
  }
}