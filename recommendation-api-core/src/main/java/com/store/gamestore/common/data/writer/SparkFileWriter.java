package com.store.gamestore.common.data.writer;

import org.apache.spark.sql.Dataset;

public interface SparkFileWriter {

  void writeFile(Dataset<?> dataset, String path);
}
