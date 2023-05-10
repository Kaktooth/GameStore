package com.store.gamestore.service.recommendation;

import com.store.gamestore.consumer.KafkaLatestRecordConsumer;
import com.store.gamestore.model.util.UserHolder;
import com.store.gamestore.persistence.entity.UserRecommendation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserRecommendationServiceImpl implements UserRecommendationService {

  private final UserHolder userHolder;
  @Qualifier("KafkaUserRecommendationConsumer")
  private final KafkaLatestRecordConsumer<List<UserRecommendation>> userRecommendationConsumer;

  @Override
  public List<UserRecommendation> getRecommendations() {
    var userId = userHolder.getAuthenticated().getId();
    return userRecommendationConsumer.getRecord(userId);
  }

  @Override
  public List<UserRecommendation> getBestRecommendations() {
    var userRecommendations = getRecommendations();
    return userRecommendations.stream()
        .distinct()
        .limit(12)
        .toList();
  }
}
