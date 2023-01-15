package com.store.gamestore.service.store.banner;

import com.store.gamestore.persistence.entity.StoreBanner;
import com.store.gamestore.persistence.repository.CommonRepository;
import com.store.gamestore.service.AbstractService;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StoreBannerService extends AbstractService<StoreBanner, UUID> {

  public StoreBannerService(CommonRepository<StoreBanner, UUID> repository) {
    super(repository);
  }
}
