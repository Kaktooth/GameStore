package com.store.gamestore.service.recommendation;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.store.gamestore.common.AppConstraints.KafkaTopics;
import com.store.gamestore.persistence.entity.UserRecommendation;
import java.time.Duration;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserRecommendationServiceImpl implements UserRecommendationService {

  private final KafkaTemplate<UUID, List<UserRecommendation>> kafkaTemplate;
  private final ConsumerFactory<UUID, List<UserRecommendation>> consumerFactory;
  private final ObjectMapper objectMapper;

  @Override
  public List<UserRecommendation> getRecommendations() {
    log.info("Getting recommendations in: {}", getClass().getSimpleName());
    kafkaTemplate.setConsumerFactory(consumerFactory);
    var records = kafkaTemplate.receive(KafkaTopics.USER_RECOMMENDATIONS, 0, 0);
    if (records != null) {
      var referenceType = new TypeReference<List<UserRecommendation>>() {};
      return objectMapper.convertValue(records.value(), referenceType);
    } else {
      throw new RuntimeException();
    }
  }
}
