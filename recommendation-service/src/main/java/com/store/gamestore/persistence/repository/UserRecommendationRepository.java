package com.store.gamestore.persistence.repository;

import com.store.gamestore.persistence.entity.UserRecommendation;
import java.util.List;
import java.util.UUID;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRecommendationRepository extends MongoRepository<UserRecommendation, UUID> {

  List<UserRecommendation> findAllByGameIdOrderByPredictedRatingDesc(UUID gameId);

  UserRecommendation findByUserIdAndGameIdAndTopicId(UUID userId, UUID gameId, Integer topicId);

  List<UserRecommendation> findAllByUserIdOrderByPredictedRatingDesc(UUID userId);

  List<UserRecommendation> findAllByUserIdAndTopicIdOrderByPredictedRatingDesc(UUID userId,
      Integer topic);

  void deleteAllByGameId(UUID gameId);

  Boolean existsByGameIdAndPredictedRating(UUID gameId, Double predictedRating);
}
