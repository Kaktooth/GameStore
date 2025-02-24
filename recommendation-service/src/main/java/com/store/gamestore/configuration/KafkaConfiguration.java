package com.store.gamestore.configuration;

import com.store.gamestore.common.ApplicationConstants.KafkaTopics;
import com.store.gamestore.persistence.entity.UserInteraction;
import com.store.gamestore.persistence.entity.UserInteractionRemoval;
import com.store.gamestore.service.UserInteractionsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.TopicBuilder;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class KafkaConfiguration {

  private final UserInteractionsService userInteractionsService;

  @Bean
  public NewTopic userRecommendationsTopic() {
    return TopicBuilder.name(KafkaTopics.USER_RECOMMENDATIONS)
        .partitions(6)
        .compact()
        .build();
  }

  @Bean
  public NewTopic gameRecommendationsTopic() {
    return TopicBuilder.name(KafkaTopics.GAME_RECOMMENDATIONS)
        .partitions(6)
        .compact()
        .build();
  }

  @Bean
  public NewTopic vocabularyTopic() {
    return TopicBuilder.name(KafkaTopics.TOPIC_VOCABULARY)
        .partitions(6)
        .compact()
        .build();
  }

  @Bean
  public NewTopic userMetricsTopic() {
    return TopicBuilder.name(KafkaTopics.USER_METRICS)
        .partitions(6)
        .compact()
        .build();
  }

  @Bean
  public NewTopic gameMetricsTopic() {
    return TopicBuilder.name(KafkaTopics.GAME_METRICS)
        .partitions(6)
        .compact()
        .build();
  }

  @Bean
  public NewTopic recommenderMetricsTopic() {
    return TopicBuilder.name(KafkaTopics.RECOMMENDER_METRICS)
        .partitions(6)
        .compact()
        .build();
  }

  @Bean
  public NewTopic popularGamesTopic() {
    return TopicBuilder.name(KafkaTopics.POPULAR_GAMES)
        .partitions(3)
        .compact()
        .build();
  }

  @Bean
  public NewTopic mostPurchasedGamesTopic() {
    return TopicBuilder.name(KafkaTopics.MOST_PURCHASED_GAMES)
        .partitions(3)
        .compact()
        .build();
  }

  @Bean
  public NewTopic favoriteGamesTopic() {
    return TopicBuilder.name(KafkaTopics.FAVORITE_GAMES)
        .partitions(3)
        .compact()
        .build();
  }

  @Bean
  public NewTopic userInteractionsForChartTopic() {
    return TopicBuilder.name(KafkaTopics.USER_INTERACTION_METRICS)
        .partitions(3)
        .compact()
        .build();
  }

  @KafkaListener(topics = KafkaTopics.USER_INTERACTIONS, groupId = KafkaTopics.USER_INTERACTIONS)
  public void onEvent(UserInteraction userInteraction) {
    if (userInteraction != null) {
      log.info("Received interaction: {}", userInteraction);
      userInteractionsService.save(userInteraction);
    } else {
      log.error("Received null object");
    }
  }

  @KafkaListener(topics = KafkaTopics.USER_INTERACTION_REMOVALS, groupId = KafkaTopics.USER_INTERACTION_REMOVALS)
  public void onEvent(UserInteractionRemoval userInteraction) {
    if (userInteraction != null) {
      log.info("Received interaction removal: {}", userInteraction);
      userInteractionsService.deleteAllUserInteractionsByRemoval(userInteraction);
    } else {
      log.error("Received null object");
    }
  }
}

