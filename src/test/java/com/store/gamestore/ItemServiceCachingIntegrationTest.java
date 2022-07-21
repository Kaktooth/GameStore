package com.store.gamestore;

import com.store.gamestore.config.CacheConfig;
import com.store.gamestore.model.entity.StoreBannerItem;
import com.store.gamestore.repository.CommonRepository;
import com.store.gamestore.service.store.banner.StoreBannerService;
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
import org.testng.Assert;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.times;

@Import({CacheConfig.class, StoreBannerService.class})
@ExtendWith(SpringExtension.class)
@EnableCaching
@ImportAutoConfiguration(classes = {
    CacheAutoConfiguration.class,
    RedisAutoConfiguration.class
})
public class ItemServiceCachingIntegrationTest {

    @MockBean
    private CommonRepository<StoreBannerItem, UUID> mockRepository;

    @Autowired
    private CommonRepository<StoreBannerItem, UUID> storeBannerService;

    @Autowired
    private CacheManager cacheManager;

    @Test
    void givenRedisCaching_whenFindItemById_thenItemReturnedFromCache() {
        List<StoreBannerItem> itemCacheMiss = storeBannerService.getAll();
        List<StoreBannerItem> itemCacheHit = storeBannerService.getAll();
        List<StoreBannerItem> itemCacheHitAgain = storeBannerService.getAll();
        System.out.println("******************************************************************************************************** " +
            "CACHE NAMES: " + cacheManager.getCacheNames() + cacheManager.getCache("storeBannerItemsCached").getNativeCache().toString());
        Assert.assertEquals(itemCacheHit, itemCacheMiss);

        Mockito.verify(mockRepository, times(1)).getAll();
    }
}
