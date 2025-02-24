package com.store.gamestore.metrics.impl;

import com.store.gamestore.metrics.UsedItemInteractionCalculator;
import com.store.gamestore.persistence.entity.InteractionType;
import com.store.gamestore.persistence.repository.UserInteractionRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UsedItemInteractionCalculatorImpl implements UsedItemInteractionCalculator {

  private final UserInteractionRepository userInteractionRepository;

  @Override
  public Integer getUsedGamesInteractions(UUID userId, InteractionType interaction,
      boolean recommended) {
    var pair = getUsedAndUnusedGames(userId, interaction, recommended);
    return pair.getRight();
  }

  @Override
  public Integer getNotUsedGamesInteractions(UUID userId, InteractionType interaction,
      boolean recommended) {
    var pair = getUsedAndUnusedGames(userId, interaction, recommended);
    return pair.getLeft();
  }

  @Override
  public Integer getUsedRecommenderInteractions(String recommender, InteractionType interaction) {
    var pair = getUsedAndUnusedRecommenders(recommender, interaction);
    return pair.getRight();
  }

  @Override
  public Integer getNotUsedRecommenderInteractions(String recommender, InteractionType interaction) {
    var pair = getUsedAndUnusedRecommenders(recommender, interaction);
    return pair.getLeft();
  }

  private Pair<Integer, Integer> getUsedAndUnusedGames(UUID userId, InteractionType interaction,
      boolean recommended) {
    var games = userInteractionRepository.getAllInteractedGamesByUserId(userId);
    var usedItemsCount = 0;
    var notUsedItemsCount = 0;
    for (var gameId : games) {
      var isVisited = userInteractionRepository.userInteractionExists(userId, gameId,
          InteractionType.VISITED, recommended);
      var isUsed = userInteractionRepository.userInteractionExists(userId, gameId, interaction,
          recommended);
      if (isVisited.orElse(false) && isUsed.orElse(false)) {
        usedItemsCount++;
      } else {
        notUsedItemsCount++;
      }
    }
    return new ImmutablePair<>(usedItemsCount, notUsedItemsCount);
  }

  private Pair<Integer, Integer> getUsedAndUnusedRecommenders(String recommender,
      InteractionType interaction) {
    var games = userInteractionRepository.getAllInteractedGamesByRecommenderName(recommender);
    var usedItemsCount = 0;
    var notUsedItemsCount = 0;
    for (var gameId : games) {
      var isVisited = userInteractionRepository.recommenderInteractionExists(recommender, gameId,
          InteractionType.VISITED);
      var isUsed = userInteractionRepository.recommenderInteractionExists(recommender, gameId,
          interaction);
      if (isVisited.orElse(false) && isUsed.orElse(false)) {
        usedItemsCount++;
      } else {
        notUsedItemsCount++;
      }
    }
    return new ImmutablePair<>(usedItemsCount, notUsedItemsCount);
  }
}