package com.store.gamestore.metrics.recommender;

import com.store.gamestore.metrics.RecommenderMetric;
import com.store.gamestore.persistence.entity.CalculatedMetric;
import com.store.gamestore.persistence.entity.InteractionType;
import com.store.gamestore.persistence.entity.MetricComparingType;
import com.store.gamestore.persistence.entity.MetricType;
import com.store.gamestore.persistence.entity.RecommenderType;
import com.store.gamestore.service.UserInteractionsService;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class BoughtRecallRecommenderMetric implements RecommenderMetric {

  private final UserInteractionsService userInteractionsService;

  @Override
  public CalculatedMetric calculateMetric(UUID recommenderId) {
    var metricName = getClass().getSimpleName();
    var recommenderName = Arrays.stream(RecommenderType.values())
        .filter(rec -> rec.getId().equals(recommenderId))
        .collect(Collectors.toList()).iterator().next()
        .getName();
    log.info("calculating: {}", metricName);
    var recommendedGames = BigDecimal.valueOf(
        userInteractionsService.countAllRecommenderInteractions(recommenderName,
            InteractionType.BOUGHT).orElse(0));
    var notRecommendedGames = BigDecimal.valueOf(
        userInteractionsService.countAllRecommenderInteractions("nonPersonal",
            InteractionType.BOUGHT).orElse(0));
    var recall = BigDecimal.valueOf(0);
    try {
      recall = recommendedGames.divide(recommendedGames.add(notRecommendedGames), 3, RoundingMode.HALF_UP);
    } catch (ArithmeticException exception) {
      log.error(exception.toString());
    }
    return new CalculatedMetric(UUID.randomUUID(), recommenderId, recall, metricName,
        recommenderName, MetricType.RECOMMENDER, MetricComparingType.HIGHER);
  }
}
