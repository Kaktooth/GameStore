package com.store.gamestore.config;

import com.store.gamestore.common.AppConstraints.CacheDuration;
import com.store.gamestore.common.AppConstraints.CacheKeys;
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
        .withCacheConfiguration(CacheKeys.BANNER_ITEMS,
            RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(CacheDuration.LONG)))
        .withCacheConfiguration(CacheKeys.POPULAR_GAMES,
            RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(CacheDuration.NORMAL)))
        .withCacheConfiguration(CacheKeys.BEST_SELLER_GAMES,
            RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(CacheDuration.NORMAL)))
        .withCacheConfiguration(CacheKeys.FAVORITE_GAMES,
            RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(CacheDuration.NORMAL)))
        .withCacheConfiguration(CacheKeys.BEST_RECOMMENDED_GAMES,
            RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(CacheDuration.NORMAL)))
        .withCacheConfiguration(CacheKeys.RECOMMENDED_GAMES_BY_TOPIC,
            RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(CacheDuration.NORMAL)))
        .withCacheConfiguration(CacheKeys.TOPIC_VOCABULARY,
            RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(CacheDuration.NORMAL)))
        .withCacheConfiguration(CacheKeys.GAME_CATEGORIES,
            RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(CacheDuration.NORMAL)))
        .withCacheConfiguration(CacheKeys.RECOMMENDED_GAMES_BY_CATEGORY,
            RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(CacheDuration.NORMAL)));
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
