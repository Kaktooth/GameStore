package com.store.gamestore.service;

import com.store.gamestore.persistence.entity.GameRating;
import java.util.List;
import java.util.UUID;

public interface GameRatingService {

  List<GameRating> getRatingsByUserId(UUID userId);

  GameRating getRatingForGame(UUID gameId, UUID userId);
}
