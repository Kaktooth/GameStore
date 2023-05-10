package com.store.gamestore.metrics.recommender;

import com.store.gamestore.metrics.Metric;
import com.store.gamestore.metrics.RecommenderMetric;
import com.store.gamestore.metrics.UsedItemInteractionCalculator;
import com.store.gamestore.persistence.entity.CalculatedMetric;
import com.store.gamestore.persistence.entity.InteractionType;
import com.store.gamestore.persistence.entity.MetricType;
import com.store.gamestore.persistence.entity.RecommenderType;
import java.util.Arrays;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class RecommenderFalsePositiveBoughtRate implements RecommenderMetric {

  private final UsedItemInteractionCalculator usedItemInteractionCalculator;

  @Override
  public CalculatedMetric calculateMetric(UUID recommenderId) {
    var metricName = getClass().getSimpleName();
    log.info("calculating: {}", metricName);
    var recommenderName = Arrays.stream(RecommenderType.values())
        .filter(rec -> rec.getId().equals(recommenderId))
        .collect(Collectors.toList()).iterator().next()
        .getName();
    var notUsedRecommendedGames = usedItemInteractionCalculator.getNotUsedRecommenderInteractions(
        recommenderName, InteractionType.BOUGHT);
    var notUsedGames = usedItemInteractionCalculator.getNotUsedRecommenderInteractions(
        "", InteractionType.BOUGHT);
    var rate = 0.0d;
    try {
      rate = notUsedRecommendedGames / (notUsedRecommendedGames + notUsedGames);
    } catch (ArithmeticException exception) {
      log.error(exception.toString());
    }
    return new CalculatedMetric(UUID.randomUUID(), recommenderId, rate, metricName, recommenderName,
        MetricType.RECOMMENDER);
  }
}