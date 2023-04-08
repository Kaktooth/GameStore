package com.store.gamestore.common;

import lombok.experimental.UtilityClass;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructType;

@UtilityClass
public class ApplicationConstants {

  @UtilityClass
  public class RecommenderConstants {

    public static final int SCHEDULER_RATE = 60000;
    public static final int GAME_RECOMMENDATIONS_LIMIT = 12;
  }

  @UtilityClass
  public class MetricsConstants {

    public static final int SCHEDULER_RATE = 30000;
  }

  @UtilityClass
  public class DatasetSchemes {

    public static final StructType TAGGED_TOKENS_SCHEMA = new StructType()
        .add(Columns.TITLE_COLUMN, DataTypes.StringType)
        .add(Columns.FILTERED_TOKENS_COLUMN, DataTypes.createArrayType(DataTypes.StringType))
        .add(Columns.POS_TAGS_COLUMN, DataTypes.createArrayType(DataTypes.StringType));
    public static final StructType FILTERED_TOKENS_SCHEMA = new StructType()
        .add(Columns.TITLE_COLUMN, DataTypes.StringType)
        .add(Columns.FILTERED_TOKENS_COLUMN, DataTypes.createArrayType(DataTypes.StringType));
    public static final StructType DESCRIBED_TOPICS_SCHEMA = new StructType()
        .add(Columns.TOPIC_COLUMN, DataTypes.IntegerType)
        .add(Columns.TERM_INDICES_COLUMN, DataTypes.createArrayType(DataTypes.IntegerType))
        .add(Columns.TERM_WEIGHTS_COLUMN, DataTypes.createArrayType(DataTypes.DoubleType));
    public static final StructType VOCABULARY_SCHEMA = new StructType()
        .add(Columns.INDEX_COLUMN, DataTypes.LongType)
        .add(Columns.WORD_COLUMN, DataTypes.StringType);
  }

  @UtilityClass
  public class LoadingPaths {

    public static final String COUNT_VECTORIZER = "./src/main/resources/static/data/trained-models/count-vectorizer";
    public static final String VOCABULARY = "./src/main/resources/static/data/trained-models/vocabulary";
    public static final String LDA_MODEL = "./src/main/resources/static/data/trained-models/LDA";
    public static final String LDA_DESCRIBED_TOPICS = "./src/main/resources/static/data/trained-models/topics";
    public static final String GAMES_METADATA_TEXT_FILE = "./src/main/resources/static/data/video_games_small.txt";
    public static final String POS_TAGGER_MODEL = "./src/main/resources/static/data/en-pos-perceptron.bin";
  }

  @UtilityClass
  public class FileFormats {

    public static final String PARQUET = "parquet";
  }

  @UtilityClass
  public class Columns {

    public static final String VALUE_COLUMN = "value";
    public static final String WORD_COLUMN = "word";
    public static final String INDEX_COLUMN = "index";
    public static final String FEATURES_COLUMN = "features";
    public static final String TITLE_COLUMN = "title";
    public static final String SECOND_TITLE_COLUMN = "title2";
    public static final String DESCRIPTION_COLUMN = "description";
    public static final String TOKENS_COLUMN = "tokens";
    public static final String FILTERED_TOKENS_COLUMN = "filteredTokens";
    public static final String TOPIC_COLUMN = "topic";
    public static final String TERM_INDICES_COLUMN = "termIndices";
    public static final String TERM_WEIGHTS_COLUMN = "termWeights";
    public static final String POS_TAGS_COLUMN = "pos";
    public static final String TOPIC_DISTRIBUTION = "topicDistribution";
    public static final String SECOND_TOPIC_DISTRIBUTION = "topicDistribution2";
    public static final String PAIR = "pair";
    public static final String JENSEN_SHANNON_DISTANCE = "jsd";
    public static final String FIRST_COLUMN = "_1";
    public static final String SECOND_COLUMN = "_2";
    public static final String ID_COLUMN = "id";
    public static final String NORMILIZED_VALUE ="normalizedValue";
  }

  @UtilityClass
  public class Symbols {

    public static final String BAR = "|";

    @UtilityClass
    public class Patterns {

      public static final String BAR = "\\|";
    }
  }

  @UtilityClass
  public class Qualifiers {

    public static final String POS_TAGGER_FILTER = "POSTaggerFilter";
    public static final String STOP_WORDS_FILTER = "stopWordsFilter";
    public static final String LDA_RECOMMENDER = "LDARecommender";
  }

  @UtilityClass
  public class TokenizerConstants {

    public static final Integer MIN_TOKEN_LENGTH = 3;

    @UtilityClass
    public class TokenizerPatterns {

      public static final String ONLY_WORDS_AND_HYPHEN = "[^\\w-]";
    }
  }

  @UtilityClass
  public class LDAConstants {

    public static final Integer TOPICS_NUMBER = 73;
    public static final Integer MAX_ITERATIONS = 80;
    public static final Float DISTRIBUTION_OVER_TOPICS = 14f;
    public static final Float DISTRIBUTION_OVER_TERMS = 2f;
  }

  @UtilityClass
  public class UDF {

    public static final String GENERATE_RANDOM_STRING = "generateRandomString";
    public static final String CALCULATE_JENSEN_SHANNON_DISTANCE = "calculateJSD";
    public static final String NORMILIZE = "normalize";
  }

  @UtilityClass
  public class WordClasses {

    public static final String NOUN = "NOUN";
  }

  @UtilityClass
  public class KafkaTopics {

    public static final String GAME_RECOMMENDATIONS = "game-recommendations";
    public static final String USER_RECOMMENDATIONS = "user-recommendations";
    public static final String USER_INTERACTIONS = "user-interactions";
    public static final String USER_INTERACTION_REMOVALS = "user-interactions-removal";
  }
}
