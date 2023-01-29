package com.store.gamestore.common;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.experimental.UtilityClass;

@UtilityClass
public class AppConstraints {

  @UtilityClass
  public class Authentication {
    public static final String GET_USER_BY_USERNAME_QUERY = "SELECT username, password, enabled "
        + "FROM users WHERE username = ?";
    public static final String GET_AUTHORITY_BY_USERNAME_QUERY = "SELECT username, email, authority"
        + " FROM authorities WHERE username = ?";
  }

  @UtilityClass
  public class CacheNames {
    public static final String POPULAR_GAMES = "popularGamesCached";
    public static final String BEST_SELLER_GAMES = "bestSellerGamesCached";
    public static final String FAVORITE_GAMES = "favoriteGamesCached";
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
    public static final String ACCESS_DENIED_PAGE = "/access-denied-page";
    public static final String API_PAGE = "/api/**";
  }
}
