package com.store.gamestore.recommender;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

public interface DatasetFilter {

  Dataset<Row> filterDataset(Dataset<Row> inputDataset);
}
