package com.store.gamestore.persistence.repository;

import com.store.gamestore.persistence.entity.GameRecommendation;
import java.util.List;
import java.util.UUID;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GameRecommendationRepository extends MongoRepository<GameRecommendation, UUID> {

  List<GameRecommendation> findAllByFirstGameId(UUID gameId);

  List<GameRecommendation> findAllByFirstGameIdOrderBySimilarity(UUID gameId);

  GameRecommendation findFirstByFirstGameIdOrderBySimilarity(UUID gameId);

  Boolean existsByFirstGameIdAndSecondGameIdAndSimilarity(UUID firstGameId, UUID secondGameId,
      Double similarity);
}
