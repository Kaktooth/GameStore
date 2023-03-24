package com.store.gamestore.persistence.repository;

import com.store.gamestore.persistence.entity.UserRecommendation;
import java.util.List;
import java.util.UUID;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRecommendationRepository extends MongoRepository<UserRecommendation, UUID> {

  List<UserRecommendation> findAllByGameId(UUID gameId);

  List<UserRecommendation> findAllByUserId(UUID userId);

  List<UserRecommendation> findAllByTopicId(Integer topic);

  Boolean existsByGameIdAndPredictedRating(UUID gameId, Double predictedRating);
}
