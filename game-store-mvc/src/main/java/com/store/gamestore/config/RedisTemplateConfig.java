package com.store.gamestore.config;

import com.store.gamestore.persistence.entity.GameRecommendation;
import com.store.gamestore.persistence.entity.UserRecommendation;
import java.util.List;
import java.util.UUID;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

@Configuration
public class RedisTemplateConfig {

  @Bean
  public RedisTemplate<UUID, Object> uuidRedisTemplate(
      RedisConnectionFactory redisConnectionFactory) {
    RedisTemplate<UUID, Object> template = new RedisTemplate<>();
    template.setConnectionFactory(redisConnectionFactory);
    template.setKeySerializer(RedisSerializer.java());
    template.setValueSerializer(RedisSerializer.java());
    return template;
  }
}
