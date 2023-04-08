package com.store.gamestore.service.impl;

import com.store.gamestore.persistence.entity.InteractionType;
import com.store.gamestore.persistence.entity.UserInteraction;
import com.store.gamestore.persistence.entity.UserInteractionRemoval;
import com.store.gamestore.persistence.repository.UserInteractionRepository;
import com.store.gamestore.service.UserInteractionsService;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserInteractionsServiceImpl implements UserInteractionsService {

  private final UserInteractionRepository userInteractionRepository;

  @Override
  public void save(UserInteraction userInteraction) {
    userInteractionRepository.save(userInteraction);
  }

  @Override
  public List<UserInteraction> findAllByUserId(UUID userId) {
    return userInteractionRepository.findAllByUserId(userId);
  }

  @Override
  public Optional<Integer> countAllUserInteractions(UUID userId, InteractionType interactionType,
      Boolean recommended) {
    try {
      return userInteractionRepository.countAllUserInteractions(userId, interactionType,
          recommended);
    } catch (Exception ex) {
      log.error(ex.toString());
      return Optional.empty();
    }
  }

  @Override
  public List<UserInteraction> findAllUserInteractions(UUID userId, InteractionType interactionType,
      Boolean recommended) {
    return userInteractionRepository.findAllUserInteractions(userId, interactionType, recommended);
  }

  @Override
  public Optional<Integer> countAllUserInteractionsWithGame(UUID gameId,
      InteractionType interactionType, Boolean recommended) {
    try {
      return userInteractionRepository.countAllGameInteractions(gameId, interactionType,
          recommended);
    } catch (Exception ex) {
      log.error(ex.toString());
      return Optional.empty();
    }
  }

  @Override
  public Optional<Integer> countAllUserInteractionsWithGame(UUID gameId,
      InteractionType interactionType) {
    try {
      return userInteractionRepository.countAllGameInteractions(gameId, interactionType);
    } catch (Exception ex) {
      log.error(ex.toString());
      return Optional.empty();
    }
  }

  @Override
  public List<UserInteraction> getAllUserInteractionsWithGame(UUID gameId,
      InteractionType interactionType, Boolean recommended) {
    return userInteractionRepository.getAllGameInteractions(gameId, interactionType, recommended);
  }

  @Override
  public Optional<Boolean> userInteractionExists(UUID userId, UUID gameId,
      InteractionType interactionType, Boolean recommended) {
    try {
      return userInteractionRepository.userInteractionExists(userId, gameId, interactionType,
          recommended);
    } catch (Exception ex) {
      log.error(ex.toString());
      return Optional.empty();
    }
  }

  @Override
  public List<UUID> getAllInteractedGamesByUserId(UUID userId) {
    return userInteractionRepository.getAllInteractedGamesByUserId(userId);
  }

  @Override
  public List<UserInteraction> findAllUserInteractionsWithGame(UUID userId, UUID gameId,
      InteractionType interactionType) {
    return userInteractionRepository.findAllByUserIdAndGameIdAndInteractionType(userId, gameId,
        interactionType);
  }

  @Override
  public void deleteAllUserInteractionsByRemoval(UserInteractionRemoval userInteraction) {
    userInteractionRepository.deleteAllByUserIdAndGameIdAndInteractionType(
        userInteraction.getUserId(), userInteraction.getGameId(),
        userInteraction.getInteractionType());
  }

  @Override
  public Integer countMaxUserInteractions(UUID userId, InteractionType interactionType) {
    return userInteractionRepository.countMaxUserInteractions(userId, interactionType);
  }
}
