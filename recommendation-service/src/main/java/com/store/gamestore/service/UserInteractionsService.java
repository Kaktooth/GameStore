package com.store.gamestore.service;

import com.store.gamestore.persistence.entity.InteractionType;
import com.store.gamestore.persistence.entity.UserInteraction;
import com.store.gamestore.persistence.entity.UserInteractionRemoval;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserInteractionsService {

  void save(UserInteraction userInteraction);

  List<UserInteraction> findAllByUserId(UUID userId);

  Optional<Integer> countAllUserInteractions(UUID userId, InteractionType interactionType,
      Boolean recommended);

  Optional<Integer> countAllRecommenderInteractions(String recommender,
      InteractionType interactionType);

  List<UserInteraction> findAllUserInteractions(UUID userId, InteractionType interactionType,
      Boolean recommended);

  Optional<Integer> countAllUserInteractionsWithGame(UUID gameId, InteractionType interactionType,
      Boolean recommended);

  Optional<Integer> countAllUserInteractionsWithGame(UUID gameId, InteractionType interactionType);

  List<UserInteraction> getAllUserInteractionsWithGame(UUID gameId, InteractionType interactionType,
      Boolean recommended);

  Optional<Boolean> userInteractionExists(UUID userId, UUID gameId,
      InteractionType interactionType, Boolean recommended);

  List<UUID> getAllInteractedGamesByUserId(UUID userId);

  List<UserInteraction> findAllUserInteractionsWithGame(UUID userId, UUID gameId,
      InteractionType interactionType);

  void deleteAllUserInteractionsByRemoval(UserInteractionRemoval userInteraction);

  Integer countMaxUserInteractions(UUID userId, InteractionType interactionType);

  Optional<Integer> countAllGameInteractionsByDate(UUID gameId, InteractionType interactionType,
      LocalDate start, LocalDate end);

  List<UserInteraction> findAllInteractionsWithGame(UUID gameId);
}
