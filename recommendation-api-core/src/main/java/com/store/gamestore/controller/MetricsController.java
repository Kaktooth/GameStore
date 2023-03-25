package com.store.gamestore.controller;

import com.store.gamestore.persistence.entity.UserMetric;
import com.store.gamestore.service.MetricService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RestController
@RequestMapping("/metrics")
@RequiredArgsConstructor
public class MetricsController {

  private final MetricService metricService;

  @GetMapping("/{refId}")
  public List<UserMetric> getAllMetricsByRefId(@PathVariable UUID refId) {
    return metricService.getAllMetricsByReferenceId(refId);
  }

  @GetMapping("/{refId}/{metricName}")
  public List<UserMetric> getAllMetricsByRefIdAndMetricName(@PathVariable UUID refId,
      @PathVariable String metricName) {
    return metricService.getMetricsByReferenceIdAndName(refId, metricName);
  }
}
