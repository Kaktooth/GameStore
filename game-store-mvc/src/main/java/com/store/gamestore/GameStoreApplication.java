package com.store.gamestore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableCaching
@SpringBootApplication
@EnableKafka
@EnableAsync
public class GameStoreApplication {

  public static void main(String[] args) {
    SpringApplication.run(GameStoreApplication.class, args);
  }
}
