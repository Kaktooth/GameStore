package com.store.gamestore.configuration;

import org.apache.spark.SparkConf;
import org.apache.spark.sql.SparkSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SparkConfiguration {

  @Bean
  public SparkConf sparkConf(@Value("${spark.threads}") Integer localThreads,
      @Value("${spring.application.name}") String appName) {
    return new SparkConf()
        .setAppName(appName)
        .setMaster(String.format("local[%d]", localThreads))
        .set("spark.ui.enabled", "true")
        .set("spark.ui.port", "4040")
        .set("hadoop.home.dir", "C:\\hadoop-3.3.0");
  }

  @Bean
  public SparkSession sparkSession(SparkConf standardConfig) {
    return SparkSession
        .builder()
        .config(standardConfig)
        .getOrCreate();
  }
}
