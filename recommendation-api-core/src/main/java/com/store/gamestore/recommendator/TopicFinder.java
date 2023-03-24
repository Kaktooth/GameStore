package com.store.gamestore.recommendator;

import java.util.Set;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

public interface TopicFinder {

  Set<String> findPopularTopicTerms(Dataset<Row> topicsDataset, String[] vocabulary, Integer topic);
}
