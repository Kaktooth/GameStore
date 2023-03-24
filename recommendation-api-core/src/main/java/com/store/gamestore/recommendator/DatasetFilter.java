package com.store.gamestore.recommendator;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

public interface DatasetFilter {

  Dataset<Row> filterDataset(Dataset<Row> inputDataset);
}
