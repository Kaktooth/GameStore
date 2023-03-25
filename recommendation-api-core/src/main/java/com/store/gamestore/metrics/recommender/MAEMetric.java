package com.store.gamestore.metrics.recommender;

import com.store.gamestore.metrics.Metric;
import com.store.gamestore.persistence.entity.UserMetric;
import com.store.gamestore.persistence.repository.GameRatingRepository;
import com.store.gamestore.persistence.repository.GameRecommendationRepository;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MAEMetric implements Metric {

  private final GameRatingRepository gameRatingRepository;

  private final GameRecommendationRepository gameRecommendationRepository;

  @Override
  public UserMetric calculateMetric(UUID userId) {
    var gameRatings = gameRatingRepository.findAllByUserId(userId);
    var ratingsDivisionSum = 0;
    for (var rating : gameRatings) {
      var firstRating = gameRatingRepository.findFirstByGameIdAndUserIdOrderByDateTimeDesc(
          rating.getGameId(), rating.getUserId());
      var similarity = gameRecommendationRepository.findFirstByFirstGameIdOrderBySimilarity(
          rating.getGameId().toString()).getSimilarity();
      ratingsDivisionSum += Math.abs(similarity - firstRating.getRating());
    }
    var MAE = Math.sqrt((1 / gameRatings.size()) * ratingsDivisionSum);
    var metricGenerationDate = LocalDateTime.now();
    return new UserMetric(UUID.randomUUID(), userId, MAE, metricGenerationDate,
        getClass().getName());
  }
}
