package com.store.gamestore.config;

import com.store.gamestore.common.AppConstraints.CacheNames;
import java.time.Duration;
import lombok.Setter;
import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

@Configuration
@EnableCaching
@Setter
public class CacheConfig {

  @Bean
  public RedisCacheManagerBuilderCustomizer redisCacheManagerBuilderCustomizer() {
    return builder -> builder
        .withCacheConfiguration(CacheNames.POPULAR_GAMES,
            RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(48)))
        .withCacheConfiguration(CacheNames.BEST_SELLER_GAMES,
            RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(48)))
        .withCacheConfiguration(CacheNames.FAVORITE_GAMES,
            RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(48)));
  }

  @Bean
  public RedisCacheConfiguration cacheConfiguration() {
    return RedisCacheConfiguration.defaultCacheConfig()
        .entryTtl(Duration.ofHours(24))
        .disableCachingNullValues()
        .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(
            new GenericJackson2JsonRedisSerializer()));
  }
}
