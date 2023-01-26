package com.store.gamestore.common;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:message.properties")
@Getter
public class ExceptionMessage {
  @Value("${internal.server.error.message}")
  private String internalServerErrorMessage;

  @Value("${unauthorized.error.message}")
  private String unauthorizedErrorMessage;

  @Value("${bad.request.error.message}")
  private String badRequestErrorMessage;

  @Value("${access.denied.error.message}")
  private String accessDeniedErrorMessage;

  @Value("${not.found.error.message}")
  private String notFoundErrorMessage;
}
