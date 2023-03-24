package com.store.gamestore.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Slf4j
@Configuration
public class KafkaConfig {

  @Bean
  public NewTopic interactionsTopic() {
    return TopicBuilder.name("user-interactions")
        .partitions(6)
        .replicas(2)
        .compact()
        .build();
  }

  @Bean
  public NewTopic interactionsRemovalTopic() {
    return TopicBuilder.name("user-interactions-removal")
        .partitions(6)
        .replicas(2)
        .compact()
        .build();
  }
}
