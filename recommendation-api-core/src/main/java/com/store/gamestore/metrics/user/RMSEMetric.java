package com.store.gamestore.metrics.user;

import com.store.gamestore.metrics.Metric;
import com.store.gamestore.metrics.UserMetric;
import com.store.gamestore.persistence.entity.CalculatedMetric;
import com.store.gamestore.persistence.entity.MetricType;
import com.store.gamestore.persistence.repository.GameRatingRepository;
import com.store.gamestore.persistence.repository.GameRecommendationRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class RMSEMetric implements UserMetric {

  private final GameRatingRepository gameRatingRepository;
  private final GameRecommendationRepository gameRecommendationRepository;

  @Override
  public CalculatedMetric calculateMetric(UUID userId) {
    var metricName = getClass().getSimpleName();
    log.info("calculating: {}", metricName);
    var gameRatings = gameRatingRepository.findAllByUserId(userId);
    var ratingsDivisionSum = 0.0d;
    for (var rating : gameRatings) {
      var firstRating = gameRatingRepository.findFirstByGameIdAndUserIdOrderByDateTimeDesc(
          rating.getGameId(), rating.getUserId());
      var similarity = gameRecommendationRepository.findFirstByFirstGameIdOrderBySimilarity(
          rating.getGameId()).getSimilarity();
      var ratingDivision = similarity - firstRating.getRating();
      ratingsDivisionSum += Math.pow(ratingDivision, 2);
    }
    var RMSE = 0.0d;
    try {
      RMSE = Math.sqrt((1.0d / gameRatings.size()) * ratingsDivisionSum);
    } catch (ArithmeticException exception) {
      log.error(exception.toString());
    }
    return new CalculatedMetric(UUID.randomUUID(), userId, RMSE, metricName, "",
        MetricType.USER);
  }
}
