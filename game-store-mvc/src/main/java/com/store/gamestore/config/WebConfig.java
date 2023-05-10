package com.store.gamestore.config;

import com.store.gamestore.common.AppConstraints.AppPath;
import com.store.gamestore.common.AppConstraints.ExtendedAppPath;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

  @Override
  public void addViewControllers(ViewControllerRegistry registry) {
    registry.addRedirectViewController(AppPath.START_PAGE, AppPath.STORE_PAGE);
    registry.addViewController(ExtendedAppPath.GAME_UPLOAD_PAGE).setViewName("upload");
    registry.addViewController(ExtendedAppPath.UPLOADED_GAMES_PAGE).setViewName("uploaded-games");
    registry.addViewController(ExtendedAppPath.GAME_PAGE).setViewName("game");
    registry.addViewController(ExtendedAppPath.STORE_PAGE).setViewName("store");
    registry.addViewController(ExtendedAppPath.PROFILE_PAGE).setViewName("profile");
    registry.addViewController(ExtendedAppPath.LOG_IN_PAGE).setViewName("log-in");
    registry.addViewController(ExtendedAppPath.ACCOUNT_CREATION_PAGE).setViewName("create-account");
    registry.addViewController(ExtendedAppPath.ACCOUNT_PAGE).setViewName("account");
    registry.addViewController(ExtendedAppPath.COLLECTION_PAGE).setViewName("collection");
    registry.addViewController(ExtendedAppPath.PAYMENT_INFO_PAGE).setViewName("payment-info");
    registry.addViewController(ExtendedAppPath.PURCHASE_PAGE).setViewName("purchase");
    registry.addViewController(ExtendedAppPath.RECOMMENDATIONS_METRICS).setViewName("recommendations-metrics");
    registry.addViewController(ExtendedAppPath.ERROR_PAGE).setViewName("error");
    registry.addViewController(ExtendedAppPath.ERROR_PAGE)
        .setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
    registry.addViewController(ExtendedAppPath.ACCESS_DENIED_PAGE)
        .setStatusCode(HttpStatus.FORBIDDEN);
  }
}
