package com.store.gamestore.service;

import com.store.gamestore.persistence.entity.GameRecommendation;
import com.store.gamestore.persistence.entity.UserRecommendation;
import java.util.List;
import java.util.UUID;

public interface RecommenderService {

  void recommend();

  List<GameRecommendation> getRecommendationsByGameId(UUID gameId);

  List<UserRecommendation> getRecommendationsByUserId(UUID userId);

  List<UserRecommendation> getRecommendationsByUserAndTopic(UUID userId, Integer topicId);
}
