package com.store.gamestore.common.data.loader;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.springframework.stereotype.Component;

@Component
public class SparkParquetLoader implements SparkFileLoader<Dataset<Row>> {


  @Override
  public Dataset<Row> loadTextFile(String path) {
    return null;
  }

  @Override
  public Dataset<Row> loadModel(String path) {
    return null;
  }

  @Override
  public Dataset<Row> loadDataset(String path) {
    return null;
  }
}
