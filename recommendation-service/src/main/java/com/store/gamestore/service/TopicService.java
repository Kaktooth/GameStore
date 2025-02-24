package com.store.gamestore.service;

import java.util.Map;
import java.util.Set;

public interface TopicService {
  Map<Integer, Set<String>> getTopics();

  Integer getTopicCount();
}
