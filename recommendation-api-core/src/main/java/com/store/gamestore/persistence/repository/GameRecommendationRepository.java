package com.store.gamestore.persistence.repository;

import com.store.gamestore.persistence.entity.GameRecommendation;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GameRecommendationRepository extends MongoRepository<GameRecommendation, String> {

  List<GameRecommendation> findAllByFirstGameId(String gameId);

  Boolean existsByFirstGameIdAndSecondGameIdAndSimilarity(String firstGameId, String secondGameId,
      Double similarity);
}
