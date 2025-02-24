package com.store.gamestore.service.recommendation;

import java.util.Map;
import java.util.Set;

public interface TopicService {

  Map<Integer, Set<String>> getTopics();
}
