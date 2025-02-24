package com.store.gamestore.persistence.repository;

import com.store.gamestore.persistence.entity.GameRating;
import java.util.List;
import java.util.UUID;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GameRatingRepository extends MongoRepository<GameRating, UUID> {

  List<GameRating> findAllByUserId(UUID userId);

  List<GameRating> findAllByUserIdAndGameId(UUID userId, UUID gameId);

  Boolean existsByGameIdAndUserIdAndRating(UUID gameId, UUID userId, Double rating);

  List<GameRating> findDistinctFirstByUserIdOrderByDateTimeDesc(UUID userId);

  GameRating findFirstByGameIdAndUserIdOrderByDateTimeDesc(UUID gameId, UUID userId);
}
