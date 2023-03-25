package com.store.gamestore.recommender;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

public interface DatasetFilterer {

  DatasetFiltererImpl applyFilter(DatasetFilter filter);

  Dataset<Row> collect();
}
