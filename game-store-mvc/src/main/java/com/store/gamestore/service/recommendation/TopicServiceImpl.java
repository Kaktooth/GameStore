package com.store.gamestore.service.recommendation;

import com.store.gamestore.common.AppConstraints.KafkaTopics;
import com.store.gamestore.consumer.KafkaLatestRecordConsumer;
import java.util.Map;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TopicServiceImpl implements TopicService {

  private final KafkaLatestRecordConsumer<Map<Integer, Set<String>>> topicsConsumer;

  @Override
  public Map<Integer, Set<String>> getTopics() {
    return topicsConsumer.getRecord(KafkaTopics.TOPIC_VOCABULARY_ID);
  }
}
