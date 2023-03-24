package com.store.gamestore.recommendator;

import org.apache.spark.ml.Model;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

public interface FeaturesExtractor<T extends Model<T>> {

  Dataset<Row> extractFeatures(Dataset<Row> inputDataset);

  T getExtractorModule();
}
