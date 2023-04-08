package com.store.gamestore.config;

import com.store.gamestore.common.AppConstraints.KafkaTopics;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class KafkaConfig {

//  private final KafkaProperties kafkaProperties;
//  private final ObjectMapper objectMapper;
//  private final RedisTemplate<UUID, List<GameRecommendation>> gamesRedisTemplate;
//
//  @Bean
//  public KafkaLatestRecordConsumer<List<GameRecommendation>> gameRecommendationsConsumer() {
//    return new KafkaLatestRecordConsumerImpl<>(kafkaProperties, objectMapper, gamesRedisTemplate);
//  }

  @Bean
  public NewTopic interactionsTopic() {
    return TopicBuilder.name(KafkaTopics.USER_INTERACTIONS)
        .partitions(6)
        .compact()
        .build();
  }

  @Bean
  public NewTopic interactionsRemovalTopic() {
    return TopicBuilder.name(KafkaTopics.USER_INTERACTION_REMOVALS)
        .partitions(6)
        .compact()
        .build();
  }
}
