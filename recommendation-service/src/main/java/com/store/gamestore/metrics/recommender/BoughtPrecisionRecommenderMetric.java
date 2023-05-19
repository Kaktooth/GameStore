package com.store.gamestore.metrics.recommender;

import com.store.gamestore.metrics.RecommenderMetric;
import com.store.gamestore.metrics.UsedItemInteractionCalculator;
import com.store.gamestore.persistence.entity.CalculatedMetric;
import com.store.gamestore.persistence.entity.InteractionType;
import com.store.gamestore.persistence.entity.MetricComparingType;
import com.store.gamestore.persistence.entity.MetricType;
import com.store.gamestore.persistence.entity.RecommenderType;
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
public class BoughtPrecisionRecommenderMetric implements RecommenderMetric {

  private final UsedItemInteractionCalculator usedItemInteractionCalculator;

  @Override
  public CalculatedMetric calculateMetric(UUID recommenderId) {
    var metricName = getClass().getSimpleName();
    var recval = RecommenderType.values();
    var recommenderName = Arrays.stream(recval)
        .filter(rec -> rec.getId().equals(recommenderId))
        .collect(Collectors.toList()).get(0)
        .getName();
    log.info("calculating: {}", metricName);
    var usedGames = BigDecimal.valueOf(
        usedItemInteractionCalculator.getUsedRecommenderInteractions(recommenderName,
            InteractionType.BOUGHT));
    var notUsedGames = BigDecimal.valueOf(
        usedItemInteractionCalculator.getNotUsedRecommenderInteractions(
            recommenderName, InteractionType.BOUGHT));
    var precision = BigDecimal.valueOf(0);
    try {
      precision = usedGames.divide(usedGames.add(notUsedGames), 3, RoundingMode.HALF_UP);
    } catch (ArithmeticException exception) {
      log.error(exception.toString());
    }
    return new CalculatedMetric(UUID.randomUUID(), recommenderId, precision, metricName,
        recommenderName, MetricType.RECOMMENDER, MetricComparingType.HIGHER);
  }
}