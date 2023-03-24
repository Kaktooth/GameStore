package com.store.gamestore.recommendator;

import com.store.gamestore.common.ApplicationConstants.Columns;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.spark.ml.feature.StopWordsRemover;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.springframework.stereotype.Component;

@Component
public class StopWordsFilter implements DatasetFilter {

  private final String[] additionalStopWords = new String[]{
      "game", "games", "video", "gaming", "gamers", "new", "final", "way", "set", "genre", "system",
      "move", "design", "level", "levels"
  };

  @Override
  public Dataset<Row> filterDataset(Dataset<Row> inputDataset) {
    var remover = new StopWordsRemover()
        .setInputCol(Columns.TOKENS_COLUMN)
        .setOutputCol(Columns.FILTERED_TOKENS_COLUMN);

    var stopWords = ArrayUtils.addAll(remover.getStopWords(), additionalStopWords);
    remover.setStopWords(stopWords);
    return remover.transform(inputDataset);
  }
}
