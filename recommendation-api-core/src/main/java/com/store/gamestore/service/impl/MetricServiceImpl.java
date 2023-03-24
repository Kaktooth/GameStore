package com.store.gamestore.service.impl;

import com.store.gamestore.common.ApplicationConstants.MetricsConstants;
import com.store.gamestore.metrics.MetricCalculator;
import com.store.gamestore.persistence.entity.UserMetric;
import com.store.gamestore.persistence.repository.MetricRepository;
import com.store.gamestore.persistence.repository.UserRepository;
import com.store.gamestore.service.MetricService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MetricServiceImpl implements MetricService {

  private final MetricCalculator metricCalculator;
  private final UserRepository userRepository;
  private final MetricRepository metricRepository;

  @Scheduled(fixedDelay = MetricsConstants.SCHEDULER_RATE)
  public void calculateMetrics() {
    var users = userRepository.findAll();
    users.forEach(user -> metricCalculator.calculateMetrics(user.getId()));
  }

  @Override
  public List<UserMetric> getAllMetricsByUserId(UUID userId) {
    return metricRepository.findAllByUserId(userId);
  }

  @Override
  public List<UserMetric> getMetricsByUserIdAndName(UUID userId, String metricName) {
    return metricRepository.findAllByUserIdAndMetricName(userId, metricName);
  }
}
