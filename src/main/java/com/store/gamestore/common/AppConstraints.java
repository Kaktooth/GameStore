package com.store.gamestore.common;

import lombok.experimental.UtilityClass;

@UtilityClass
public class AppConstraints {

  @UtilityClass
  public class AppPath {

    public static final String ERROR_PAGE = "/error";
    public static final String LOG_IN_PAGE = "/log-in";
    public static final String ACCOUNT_CREATION_PAGE = "/create-account";
    public static final String ACCESS_DENIED_PAGE = "/access-denied-page";
    public static final String STORE_PAGE = "/store";
    public static final String API_PAGE = "/api/**";
    public static final String START_PAGE = "/";
    public static final String PROFILE_PAGE = "/profile";
    public static final String GAME_PAGE = "/game/**";
    public static final String ERROR_ATTRIBUTE = "?error";
  }
}
