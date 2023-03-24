package com.store.gamestore.model.util;

import com.store.gamestore.model.dto.StoreBannerDTO;
import com.store.gamestore.persistence.entity.StoreBanner;
import java.io.IOException;

public interface StoreBannerMapper {

  StoreBanner destinationToSource(StoreBannerDTO storeBannerDto) throws IOException;
}
