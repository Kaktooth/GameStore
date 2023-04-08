package com.store.gamestore.recommender.impl;

import com.store.gamestore.recommender.FeaturesExtractor;
import com.store.gamestore.recommender.TrainedModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.spark.ml.feature.CountVectorizerModel;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CountVectorizerFeaturesExtractor implements FeaturesExtractor<CountVectorizerModel> {

  private final TrainedModel<CountVectorizerModel> countVectorizer;

  @Override
  public Dataset<Row> extractFeatures(Dataset<Row> inputDataset) {
    log.info("extract features...");
    Dataset<Row> features = countVectorizer.getTrainedModel().transform(inputDataset);
    features.show(false);
    return features;
  }

  @Override
  public CountVectorizerModel getExtractorModule() {
    return (CountVectorizerModel) countVectorizer.getTrainedModel();
  }
}
