package com.store.gamestore.service.pictures;

import com.store.gamestore.persistence.entity.Image;
import com.store.gamestore.persistence.repository.CommonRepository;
import com.store.gamestore.service.AbstractService;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class ImageService extends AbstractService<Image, UUID> {

  public ImageService(
      CommonRepository<Image, UUID> repository) {
    super(repository);
  }
}
