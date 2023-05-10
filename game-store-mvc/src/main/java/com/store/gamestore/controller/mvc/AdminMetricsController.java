package com.store.gamestore.controller.mvc;

import com.store.gamestore.model.util.UserHolder;
import com.store.gamestore.service.metrics.MetricsService;
import java.util.HashMap;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/metrics")
@RequiredArgsConstructor
public class AdminMetricsController {

  private final MetricsService metricsService;
  private final UserHolder userHolder;

  @GetMapping
  public String getAdminPageWithMetrics(Model model) {
    var recommenderMetrics = metricsService.getCalculatedRecommenderMetrics();
    var userMetrics = metricsService.getCalculatedUserMetrics();

    //TODO add collection that consists of metrics names and boolean that this metric higher is
    // better that lower or lower is better that higher
    var bestValues = new HashMap<Boolean, HashMap<String, Double>>();
    var highestValues = new HashMap<String, Double>();
    var lowestValues = new HashMap<String, Double>();
    for (var metrics : recommenderMetrics.entrySet()) {
      for (var metric : metrics.getValue()) {
        var key = metric.getMetricName();
        var currentHighestValue = highestValues.get(key);
        var currentLowestValue = lowestValues.get(key);
        if (currentHighestValue == null || metric.getValue() > currentHighestValue) {
          highestValues.put(key, metric.getValue());
        }
        if (currentLowestValue == null || metric.getValue() < currentLowestValue) {
          lowestValues.put(key, metric.getValue());
        }
      }
    }
    bestValues.put(true, highestValues);
    bestValues.put(false, lowestValues);

    model.addAttribute("user", userHolder.getAuthenticated());
    model.addAttribute("recommenderMetrics", recommenderMetrics);
    model.addAttribute("userMetrics", userMetrics);
    model.addAttribute("bestValues", bestValues);
    return "recommendations-metrics";
  }
}
