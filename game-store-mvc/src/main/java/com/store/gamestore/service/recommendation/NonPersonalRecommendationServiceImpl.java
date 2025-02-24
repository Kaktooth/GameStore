package com.store.gamestore.service.recommendation;

import com.store.gamestore.common.AppConstraints.KafkaTopics;
import com.store.gamestore.consumer.KafkaLatestRecordConsumer;
import com.store.gamestore.persistence.entity.Game;
import com.store.gamestore.persistence.entity.GameInteraction;
import com.store.gamestore.service.game.GameService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class NonPersonalRecommendationServiceImpl implements NonPersonalRecommendationService {

  private final KafkaLatestRecordConsumer<List<GameInteraction>> nonPersonalRecommendationConsumer;
  private final GameService gameService;

  @Override
  public List<Game> getPopularGames() {
    var popularGames = nonPersonalRecommendationConsumer.getRecord(KafkaTopics.POPULAR_GAMES_ID);
    return getGames(popularGames);
  }

  @Override
  public List<Game> getMostPurchasedGames() {
    var mostPurchasedGames = nonPersonalRecommendationConsumer.getRecord(
        KafkaTopics.MOST_PURCHASED_GAMES_ID);
    return getGames(mostPurchasedGames);
  }

  @Override
  public List<Game> getFavoriteGames() {
    var favoriteGames = nonPersonalRecommendationConsumer.getRecord(KafkaTopics.FAVORITE_GAMES_ID);
    return getGames(favoriteGames);
  }

  private List<Game> getGames(List<GameInteraction> games) {
    return games.stream()
        .map(gameInteractions -> gameService.get(gameInteractions.getGameId()))
        .toList();
  }
}
