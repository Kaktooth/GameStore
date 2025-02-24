package com.store.gamestore.config;

import java.time.LocalDate;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:user.properties")
@Getter
public class UserFilteringSettingsConfig {

  private final LocalDate releaseDate = LocalDate.MIN;
  @Value("${minGamesPrice}")
  private Double minGamesPrice;
  @Value("${maxGamesPrice}")
  private Double maxGamesPrice;
}
