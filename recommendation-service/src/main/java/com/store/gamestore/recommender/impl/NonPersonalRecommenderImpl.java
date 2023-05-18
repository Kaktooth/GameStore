package com.store.gamestore.recommender.impl;

import com.store.gamestore.common.ApplicationConstants.RecommenderConstants;
import com.store.gamestore.persistence.entity.GameInteraction;
import com.store.gamestore.persistence.entity.InteractionType;
import com.store.gamestore.persistence.repository.GameMetadataRepository;
import com.store.gamestore.recommender.NonPersonalRecommender;
import com.store.gamestore.service.UserInteractionsService;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NonPersonalRecommenderImpl implements NonPersonalRecommender {

  private final GameMetadataRepository gameMetadataRepository;
  private final UserInteractionsService userInteractionsService;

  @Override
  public List<GameInteraction> getMostInteractedGames(InteractionType interaction) {
    var sortedGames = new ArrayList<GameInteraction>();
    var games = gameMetadataRepository.findAll();
    for (var game : games) {
      var interactionsCount = userInteractionsService.countAllUserInteractionsWithGame(game.getId(),
          interaction).orElse(0);
      sortedGames.add(new GameInteraction(game.getId(), interactionsCount));
    }

    sortedGames.sort(Comparator.comparing(GameInteraction::getGameInteractions).reversed());
    return sortedGames.stream()
        .limit(RecommenderConstants.GAME_RECOMMENDATIONS_LIMIT)
        .collect(Collectors.toList());
  }
}
