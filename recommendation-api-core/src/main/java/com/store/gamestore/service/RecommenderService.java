package com.store.gamestore.service;

import com.store.gamestore.persistence.entity.UserRecommendation;
import java.util.List;
import java.util.UUID;

public interface RecommenderService {

  void recommend();

  List<UserRecommendation> getRecommendationsByGameId(UUID gameId);

  List<UserRecommendation> getRecommendationsByUserId(UUID userId);

  List<UserRecommendation> getRecommendationsByGameIds(Iterable<UUID> gameIds);
}
