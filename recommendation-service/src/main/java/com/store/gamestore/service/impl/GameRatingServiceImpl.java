package com.store.gamestore.service.impl;

import com.store.gamestore.persistence.entity.GameRating;
import com.store.gamestore.persistence.repository.GameRatingRepository;
import com.store.gamestore.service.GameRatingService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GameRatingServiceImpl implements GameRatingService {

  private final GameRatingRepository gameRatingRepository;

  @Override
  public List<GameRating> getRatingsByUserId(UUID userId) {
    return gameRatingRepository.findDistinctFirstByUserIdOrderByDateTimeDesc(userId);
  }

  @Override
  public GameRating getRatingForGame(UUID gameId, UUID userId) {
    return gameRatingRepository.findFirstByGameIdAndUserIdOrderByDateTimeDesc(gameId, userId);
  }
}
