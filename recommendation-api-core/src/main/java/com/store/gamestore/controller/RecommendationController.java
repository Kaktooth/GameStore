package com.store.gamestore.controller;

import com.store.gamestore.persistence.entity.GameRating;
import com.store.gamestore.persistence.entity.UserRecommendation;
import com.store.gamestore.service.GameRatingService;
import com.store.gamestore.service.RecommenderService;
import com.store.gamestore.service.TopicService;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/recommender")
@RequiredArgsConstructor
public class RecommendationController {

  private final RecommenderService recommenderService;
  private final GameRatingService gameRatingService;
  private final TopicService topicService;

  @GetMapping("/topics")
  public Map<Integer, Set<String>> getTopics() {
    var topics = topicService.getTopics();
    log.info("get topics: {}", topics);
    return topics;
  }

  @GetMapping("/game/{gameId}")
  public List<UserRecommendation> getRecommendationsForGame(@PathVariable UUID gameId) {
    log.info("get recommendations for game: {}", gameId);
    return recommenderService.getRecommendationsByGameId(gameId);
  }

  @GetMapping("/user/{userId}")
  public List<UserRecommendation> getRecommendationsForUser(@PathVariable UUID userId) {
    log.info("get recommendations for user: {}", userId);
    List<UserRecommendation> userRecommendations = new ArrayList<>();
    var ratedGameIds = gameRatingService.getRatingsByUserId(userId).stream()
        .sorted(Comparator.comparing(GameRating::getRating))
        .limit(5)
        .map(GameRating::getGameId)
        .collect(Collectors.toList());

    ratedGameIds.stream()
        .map(this::getRecommendationsForGame)
        .forEachOrdered(userRecommendations::addAll);

    return userRecommendations;
  }
}
