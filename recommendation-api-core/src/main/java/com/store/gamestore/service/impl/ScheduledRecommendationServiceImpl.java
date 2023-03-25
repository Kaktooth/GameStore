package com.store.gamestore.service.impl;

import com.store.gamestore.common.ApplicationConstants.Qualifiers;
import com.store.gamestore.common.ApplicationConstants.RecommenderConstants;
import com.store.gamestore.recommender.Recommender;
import com.store.gamestore.persistence.entity.UserRecommendation;
import com.store.gamestore.persistence.repository.UserRecommendationRepository;
import com.store.gamestore.service.RecommenderService;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@EnableScheduling
public class ScheduledRecommendationServiceImpl implements RecommenderService {

  @Qualifier(Qualifiers.LDA_RECOMMENDER)
  private final Recommender ldaRecommender;
  private final UserRecommendationRepository userRecommendationRepository;

  @Override
  public List<UserRecommendation> getRecommendationsByGameId(UUID gameId) {
    return userRecommendationRepository.findAllByGameId(gameId);
  }

  @Override
  public List<UserRecommendation> getRecommendationsByUserId(UUID userId) {
    return userRecommendationRepository.findAllByUserId(userId);
  }

  @Override
  public List<UserRecommendation> getRecommendationsByGameIds(Iterable<UUID> gameIds) {
    List<UserRecommendation> recommendationsIds = new ArrayList<>();
    for (var gameId : gameIds) {
      recommendationsIds.addAll(getRecommendationsByGameId(gameId));
    }
    return recommendationsIds;
  }

  @Override
  @Scheduled(fixedDelay = RecommenderConstants.SCHEDULER_RATE)
  public void recommend() {
    ldaRecommender.recommend();
  }
}
