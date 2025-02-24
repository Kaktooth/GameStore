package com.store.gamestore.service.impl;

import com.store.gamestore.recommender.FeaturesExtractor;
import com.store.gamestore.recommender.TopicFinder;
import com.store.gamestore.recommender.TrainedModel;
import com.store.gamestore.service.TopicService;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.spark.ml.clustering.LDAModel;
import org.apache.spark.ml.feature.CountVectorizerModel;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TopicServiceImpl implements TopicService {

  private final FeaturesExtractor<CountVectorizerModel> featuresExtractor;
  private final TrainedModel<LDAModel> ldaTrainedModel;
  private final TopicFinder topicFinder;

  @Override
  public Map<Integer, Set<String>> getTopics() {
    var topics = new HashMap<Integer, Set<String>>();
    var vocabulary = featuresExtractor.getExtractorModule().vocabulary();
    var ldaModel = (LDAModel) ldaTrainedModel.getTrainedModel();
    var describedTopics = ldaModel.describeTopics();
    for (int i = 0; i < describedTopics.count(); i++) {
      topics.put(i, topicFinder.findPopularTopicTerms(describedTopics, vocabulary, i));
    }
    return topics;
  }

  @Override
  public Integer getTopicCount() {
    var ldaModel = (LDAModel) ldaTrainedModel.getTrainedModel();
    var topicsCount = ldaModel.describeTopics().count();
    return Math.toIntExact(topicsCount);
  }
}
