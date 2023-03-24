package com.store.gamestore.common.data.writer;

import com.store.gamestore.common.ApplicationConstants.FileFormats;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.SaveMode;
import org.springframework.stereotype.Component;

@Component
public class SparkParquetWriter implements SparkFileWriter {

  @Override
  public void writeFile(Dataset<?> dataset, String path) {
    dataset.write()
        .mode(SaveMode.Overwrite)
        .format(FileFormats.PARQUET)
        .save(path);
  }
}
