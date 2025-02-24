package com.store.gamestore.controller.mvc;

import com.store.gamestore.model.util.UserHolder;
import com.store.gamestore.persistence.entity.CalculatedMetric;
import com.store.gamestore.persistence.entity.MetricComparingType;
import com.store.gamestore.service.metrics.MetricsService;
import java.util.HashMap;
import lombok.RequiredArgsConstructor;
import org.apache.commons.math3.util.Pair;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/metrics")
@RequiredArgsConstructor
public class AdminController {

  private final MetricsService metricsService;
  private final UserHolder userHolder;

  @GetMapping
  public String getAdminPageWithMetrics(Model model) {
    var recommenderMetrics = metricsService.getCalculatedRecommenderMetrics();
    var userMetrics = metricsService.getCalculatedUserMetrics();

    //TODO add collection that consists of metrics names and boolean that this metric higher is
    // better that lower or lower is better that higher
    var compareMap = new HashMap<String, HashMap<String, Pair<Boolean, Double>>>();
    var highestValues = new HashMap<String, Double>();
    var lowestValues = new HashMap<String, Double>();
    var metricNames = recommenderMetrics.values().iterator().next().stream()
        .map(CalculatedMetric::getMetricName)
        .toList();
    for (var metrics : recommenderMetrics.entrySet()) {
      for (var metric : metrics.getValue()) {
        var key = metric.getMetricName();
        var currentHighestValue = highestValues.get(key);
        var currentLowestValue = lowestValues.get(key);
        if (currentHighestValue == null || metric.getValue().doubleValue() > currentHighestValue) {
          highestValues.put(key, metric.getValue().doubleValue());
        }
        if (currentLowestValue == null || metric.getValue().doubleValue() < currentLowestValue) {
          lowestValues.put(key, metric.getValue().doubleValue());
        }
      }
    }
    for (var metrics : recommenderMetrics.entrySet()) {
      final var values = new HashMap<String, Pair<Boolean, Double>>();
      for (var metric : metrics.getValue()) {
        if (metric.getMetricComparingType() == MetricComparingType.HIGHER) {
          final var isBestValue = highestValues.get(metric.getMetricName())
              .equals(metric.getValue().doubleValue());
          values.put(metric.getMetricName(), new Pair<>(isBestValue, metric.getValue().doubleValue()));
        } else if (metric.getMetricComparingType() == MetricComparingType.LOWER) {
          final var isBestValue = lowestValues.get(metric.getMetricName())
              .equals(metric.getValue().doubleValue());
          values.put(metric.getMetricName(), new Pair<>(isBestValue, metric.getValue().doubleValue()));
        }
      }
      compareMap.put(metrics.getKey(), values);
    }

    model.addAttribute("user", userHolder.getAuthenticated());
    model.addAttribute("recommenderMetrics", recommenderMetrics);
    model.addAttribute("userMetrics", userMetrics);
    model.addAttribute("compareMap", compareMap);
    model.addAttribute("metricNames", metricNames);
    return "admin-metrics";
  }
}
