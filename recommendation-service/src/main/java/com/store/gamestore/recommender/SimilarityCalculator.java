package com.store.gamestore.recommender;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

public interface SimilarityCalculator {

  Dataset<Row> calculateSimilarities(Dataset<Row> dataset);
}
