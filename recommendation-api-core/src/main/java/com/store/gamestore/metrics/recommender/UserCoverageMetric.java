package com.store.gamestore.metrics.recommender;

import com.store.gamestore.metrics.Metric;
import com.store.gamestore.persistence.entity.UserMetric;
import com.store.gamestore.persistence.repository.UserInteractionRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserCoverageMetric implements Metric {

  private final UserInteractionRepository userInteractionRepository;
  @Override
  public UserMetric calculateMetric(UUID userId) {
    return null;
  }
}
