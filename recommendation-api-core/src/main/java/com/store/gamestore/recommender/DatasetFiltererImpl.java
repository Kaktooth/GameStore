package com.store.gamestore.recommender;

import lombok.AllArgsConstructor;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

@AllArgsConstructor
public class DatasetFiltererImpl implements DatasetFilterer {

  private Dataset<Row> dataset;

  @Override
  public DatasetFiltererImpl applyFilter(DatasetFilter filter) {
    dataset = filter.filterDataset(dataset);
    return this;
  }

  @Override
  public Dataset<Row> collect() {
    return dataset;
  }
}
