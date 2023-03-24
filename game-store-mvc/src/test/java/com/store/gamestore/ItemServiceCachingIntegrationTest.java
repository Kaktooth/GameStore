package com.store.gamestore;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;

import com.store.gamestore.config.CacheConfig;
import com.store.gamestore.persistence.entity.StoreBanner;
import com.store.gamestore.persistence.repository.CommonRepository;
import com.store.gamestore.service.store.banner.StoreBannerService;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.cache.CacheAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@Import({CacheConfig.class, StoreBannerService.class})
@ExtendWith(SpringExtension.class)
@EnableCaching
@ImportAutoConfiguration(classes = {
    CacheAutoConfiguration.class,
    RedisAutoConfiguration.class
})
class ItemServiceCachingIntegrationTest {

  @MockBean
  private CommonRepository<StoreBanner, UUID> mockRepository;

  @Autowired
  private CommonRepository<StoreBanner, UUID> storeBannerService;

  @Autowired
  private CacheManager cacheManager;

  @Test
  void givenRedisCaching_whenFindItemById_thenItemReturnedFromCache() {
    List<StoreBanner> itemCacheMiss = storeBannerService.findAll();
    List<StoreBanner> itemCacheHit = storeBannerService.findAll();
    List<StoreBanner> itemCacheHitAgain = storeBannerService.findAll();
    System.out.println(
        "******************************************************************************************************** "
            +
            "CACHE NAMES: " + cacheManager.getCacheNames() + cacheManager.getCache(
            "storeBannerItemsCached").getNativeCache().toString());
    assertEquals(itemCacheHit, itemCacheMiss);

    Mockito.verify(mockRepository, times(3)).findAll();
  }
}
