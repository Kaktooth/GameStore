package com.store.gamestore.recommender.impl;

import com.store.gamestore.common.ApplicationConstants.Columns;
import com.store.gamestore.common.ApplicationConstants.Qualifiers;
import com.store.gamestore.common.ApplicationConstants.TokenizerConstants;
import com.store.gamestore.common.ApplicationConstants.TokenizerConstants.TokenizerPatterns;
import com.store.gamestore.recommender.DataPreProcessor;
import com.store.gamestore.recommender.DatasetFilter;
import com.store.gamestore.recommender.impl.DatasetFiltererImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.spark.ml.feature.RegexTokenizer;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class LDADataPreProcessor implements DataPreProcessor {

  @Qualifier(Qualifiers.POS_TAGGER_FILTER)
  private final DatasetFilter posTaggerFilter;
  @Qualifier(Qualifiers.STOP_WORDS_FILTER)
  private final DatasetFilter stopWordsFilter;

  @Override
  public Dataset<Row> processTextData(Dataset<?> dataset) {
    log.info("process text data...");
    Dataset<Row> tokenizedDescriptionData = tokenizeText(dataset);

    return new DatasetFiltererImpl(tokenizedDescriptionData)
        .applyFilter(stopWordsFilter)
        .applyFilter(posTaggerFilter)
        .collect();
  }

  private Dataset<Row> tokenizeText(Dataset<?> gameMetadata) {
    log.info("tokenize text data...");
    RegexTokenizer tokenizer = new RegexTokenizer()
        .setInputCol(Columns.DESCRIPTION_COLUMN)
        .setOutputCol(Columns.TOKENS_COLUMN)
        .setPattern(TokenizerPatterns.ONLY_WORDS_AND_HYPHEN)
        .setMinTokenLength(TokenizerConstants.MIN_TOKEN_LENGTH);

    Dataset<Row> gameDescriptionTokens = tokenizer.transform(gameMetadata);
    gameDescriptionTokens.show(false);
    return gameDescriptionTokens;
  }
}
