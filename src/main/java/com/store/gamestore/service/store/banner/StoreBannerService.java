package com.store.gamestore.service.store.banner;

import com.store.gamestore.model.StoreBannerItem;
import com.store.gamestore.repository.CommonRepository;
import com.store.gamestore.service.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class StoreBannerService extends AbstractService<StoreBannerItem, UUID> {

    public StoreBannerService(CommonRepository<StoreBannerItem, UUID> repository) {
        super(repository);
    }
}
