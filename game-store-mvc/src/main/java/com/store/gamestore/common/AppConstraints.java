package com.store.gamestore.common;

import java.util.UUID;
import lombok.experimental.UtilityClass;

@UtilityClass
public class AppConstraints {

  @UtilityClass
  public class Authentication {

    public static final String GET_USER_BY_USERNAME_QUERY = """
        SELECT username, password, enabled FROM users WHERE username = ?
        """;
    public static final String GET_AUTHORITY_BY_USERNAME_QUERY = """
        SELECT username, email, authority FROM authorities
        WHERE username = ?
        """;
  }

  @UtilityClass
  public class Pagination {

    public static final int PAGE_SIZE = 4;

  }

  @UtilityClass
  public class Search {

    public static final int RANGE = 4;
  }

  @UtilityClass
  public class CacheKeys {

    public static final String BANNER_ITEMS = "bannerItems";
    public static final String POPULAR_GAMES = "popularGamesCached";
    public static final String BEST_SELLER_GAMES = "bestSellerGamesCached";
    public static final String FAVORITE_GAMES = "favoriteGamesCached";
    public static final String BEST_RECOMMENDED_GAMES = "userRecommendedBestGames";
    public static final String RECOMMENDED_GAMES_BY_TOPIC = "gamesRecommendedByTopic";
    public static final String TOPIC_VOCABULARY = "topicVocabulary";
    public static final String GAME_CATEGORIES = "categories";
    public static final String RECOMMENDED_GAMES_BY_CATEGORY = "userRecommendationsByCategory";
  }

  @UtilityClass
  public class CacheDuration {

    public static final Integer LONG = 24;
    public static final Integer NORMAL = 5;
    public static final Integer SMALL = 1;
  }

  @UtilityClass
  public class AppPath {

    public static final String ERROR_PAGE = "/error";
    public static final String LOG_IN_PAGE = "/log-in";
    public static final String ACCOUNT_CREATION_PAGE = "/create-account";
    public static final String ACCESS_DENIED_PAGE = "/access-denied-page";
    public static final String STORE_PAGE = "/store";
    public static final String START_PAGE = "/";
    public static final String PROFILE_PAGE = "/profile";
    public static final String ERROR_ATTRIBUTE = "?error";
  }

  @UtilityClass
  public class ExtendedAppPath {

    public static final String GAME_UPLOAD_PAGE = "/upload/**";
    public static final String UPLOADED_GAMES_PAGE = "/uploaded-games/**";
    public static final String GAME_PAGE = "/game/**";
    public static final String STORE_PAGE = "/store/**";
    public static final String PROFILE_PAGE = "/profile/**";
    public static final String LOG_IN_PAGE = "/log-in/**";
    public static final String ACCOUNT_CREATION_PAGE = "/create-account/**";
    public static final String ACCOUNT_PAGE = "/account/**";
    public static final String COLLECTION_PAGE = "/collection/**";
    public static final String PAYMENT_INFO_PAGE = "/payment-info/**";
    public static final String PURCHASE_PAGE = "/purchase/**";
    public static final String ERROR_PAGE = "/error/**";
    public static final String RECOMMENDATIONS_METRICS = "/recommendations-metrics/**";
    public static final String ACCESS_DENIED_PAGE = "/access-denied-page";
    public static final String API_PAGE = "/api/**";
    public static final String CSS = "/css/**";
    public static final String JS = "/js/**";
  }

  @UtilityClass
  public class KafkaTopics {

    public static final String USER_INTERACTIONS = "user-interactions";
    public static final String USER_INTERACTION_REMOVALS = "user-interactions-removal";
    public static final String TOPIC_VOCABULARY = "topic-vocabulary";
    public static final String GAME_RECOMMENDATIONS = "game-recommendations";
    public static final String USER_RECOMMENDATIONS = "user-recommendations";
    public static final String GAME_METRICS = "game-metrics";
    public static final String USER_METRICS = "user-metrics";
    public static final String RECOMMENDER_METRICS = "recommender-metrics";
    public static final String POPULAR_GAMES = "popular-games";
    public static final String MOST_PURCHASED_GAMES = "most-purchased-games";
    public static final String FAVORITE_GAMES = "favorite-games";
    public static final String USER_INTERACTION_METRICS = "user-interactions-metrics";

    public static final UUID USER_METRICS_ID = UUID.fromString(
        "6fc50041-4170-4596-84b1-352413d8d007");
    public static final UUID GAME_METRICS_ID = UUID.fromString(
        "b79ddd55-7f0b-49f6-a6ca-ddc5f0f16b73");
    public static final UUID RECOMMENDER_METRICS_ID = UUID.fromString(
        "619a4d39-d840-4626-8bbb-966179fdcec8");
    public static final UUID TOPIC_VOCABULARY_ID = UUID.fromString(
        "ab16a924-0d25-4e2d-9e08-beba7d1f9c6e");
    public static final UUID POPULAR_GAMES_ID = UUID.fromString(
        "b8473b85-efd7-4b78-a078-95bd12e59273");
    public static final UUID MOST_PURCHASED_GAMES_ID = UUID.fromString(
        "615b1da6-26f6-47ca-8d75-99c944699559");
    public static final UUID FAVORITE_GAMES_ID = UUID.fromString(
        "d2e2cce7-da3d-4193-8bd4-cf2b15c0dee1");
    public static final UUID USER_INTERACTION_METRICS_ID = UUID.fromString(
        "13acef2c-9990-415b-abcd-c28ae8945783");
  }
}
