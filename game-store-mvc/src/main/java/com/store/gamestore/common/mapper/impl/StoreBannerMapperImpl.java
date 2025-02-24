package com.store.gamestore.common.mapper.impl;

import com.store.gamestore.common.mapper.StoreBannerMapper;
import com.store.gamestore.model.dto.StoreBannerDTO;
import com.store.gamestore.persistence.entity.Image;
import com.store.gamestore.persistence.entity.StoreBanner;
import java.io.IOException;
import org.springframework.stereotype.Component;

@Component
public class StoreBannerMapperImpl implements StoreBannerMapper {

  @Override
  public synchronized StoreBanner destinationToSource(StoreBannerDTO storeBannerDto)
      throws IOException {
    StoreBanner storeBanner = new StoreBanner();
    storeBanner.setGameId(storeBannerDto.getGameId());
    storeBanner.setUserId(storeBannerDto.getUserId());
    storeBanner.setDescription(storeBannerDto.getDescription());

    final var imageData = storeBannerDto.getImageFile().getBytes();
    final var image = new Image(imageData);
    storeBanner.setImage(image);
    return storeBanner;
  }
}
