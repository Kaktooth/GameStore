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
  public Integer getUsedItemInteractions(UUID userId, InteractionType interaction,
      boolean recommended) {
    var pair = getUsedAndUnusedGames(userId, interaction, recommended);
    return pair.getRight();
  }

  @Override
  public Integer getNotUsedItemInteractions(UUID userId, InteractionType interaction,
      boolean recommended) {
    var pair = getUsedAndUnusedGames(userId, interaction, recommended);
    return pair.getLeft();
  }

  private Pair<Integer, Integer> getUsedAndUnusedGames(UUID userId, InteractionType interaction,
      boolean recommended) {
    var games = userInteractionRepository.getAllInteractedGamesByUserId(userId.toString());
    var usedItemsCount = 0;
    var notUsedItemsCount = 0;
    for (var gameId : games) {
      var isVisited = userInteractionRepository.userInteractionExists(userId.toString(),
          gameId.toString(), InteractionType.VISITED, recommended);
      var isUsed = userInteractionRepository.userInteractionExists(userId.toString(),
          gameId.toString(), interaction, recommended);
      if (isVisited && isUsed) {
        usedItemsCount++;
      } else {
        notUsedItemsCount--;
      }
    }
    return new ImmutablePair<>(usedItemsCount, notUsedItemsCount);
  }
}