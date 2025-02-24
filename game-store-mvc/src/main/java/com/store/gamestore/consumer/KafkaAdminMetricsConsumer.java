package com.store.gamestore.consumer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.store.gamestore.common.AppConstraints.KafkaTopics;
import com.store.gamestore.persistence.entity.CalculatedMetric;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaAdminMetricsConsumer implements
    KafkaLatestRecordConsumer<HashMap<String, List<CalculatedMetric>>> {

  private final ObjectMapper objectMapper;
  private final RedisTemplate<UUID, Object> redisTemplate;

  @KafkaListener(topics = {KafkaTopics.USER_METRICS, KafkaTopics.RECOMMENDER_METRICS})
  void listenMetrics(@Payload HashMap<String, List<CalculatedMetric>> calculatedMetrics,
      @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) UUID key) {
    var typeReference = new TypeReference<HashMap<String, List<CalculatedMetric>>>() {};
    var metrics = objectMapper.convertValue(calculatedMetrics, typeReference);
    redisTemplate.opsForValue().set(key, metrics);
  }

  @Override
  public HashMap<String, List<CalculatedMetric>> getRecord(UUID key) {
    return (HashMap<String, List<CalculatedMetric>>) redisTemplate.opsForValue().get(key);
  }
}
