package com.store.gamestore.service.impl;

import com.store.gamestore.common.ApplicationConstants.KafkaTopics;
import com.store.gamestore.common.ApplicationConstants.RecommenderConstants;
import com.store.gamestore.persistence.entity.GameInteraction;
import com.store.gamestore.persistence.entity.InteractionType;
import com.store.gamestore.recommender.NonPersonalRecommender;
import com.store.gamestore.service.RecommenderService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NonPersonalRecommendationServiceImpl implements RecommenderService {

  private final KafkaTemplate<UUID, List<GameInteraction>> gameInteractions;
  private final NonPersonalRecommender nonPersonalRecommender;

  @Override
  @Scheduled(fixedDelay = RecommenderConstants.SCHEDULER_RATE)
  public void recommend() {
    var popularGames = nonPersonalRecommender.getMostInteractedGames(InteractionType.VISITED);
    gameInteractions.send(KafkaTopics.POPULAR_GAMES, KafkaTopics.POPULAR_GAMES_ID,
        popularGames);

    var mostPurchasedGames = nonPersonalRecommender.getMostInteractedGames(InteractionType.BOUGHT);
    gameInteractions.send(KafkaTopics.MOST_PURCHASED_GAMES,
        KafkaTopics.MOST_PURCHASED_GAMES_ID, mostPurchasedGames);

    var favoriteGames = nonPersonalRecommender.getMostInteractedGames(InteractionType.FAVORITE);
    gameInteractions.send(KafkaTopics.FAVORITE_GAMES, KafkaTopics.FAVORITE_GAMES_ID,
        favoriteGames);
  }
}
