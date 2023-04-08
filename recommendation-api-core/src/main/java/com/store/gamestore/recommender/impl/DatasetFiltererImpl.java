package com.store.gamestore.recommender.impl;

import com.store.gamestore.recommender.DatasetFilterer;
import com.store.gamestore.recommender.Filter;
import com.store.gamestore.recommender.Filterer;
import lombok.AllArgsConstructor;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

@AllArgsConstructor
public class DatasetFiltererImpl implements DatasetFilterer {

  private Dataset<Row> dataset;

  @Override
  public Filterer<Dataset<Row>> applyFilter(Filter<Dataset<Row>> datasetFilter) {
    dataset = datasetFilter.filter(dataset);
    return this;
  }

  @Override
  public Dataset<Row> collect() {
    return dataset;
  }
}
