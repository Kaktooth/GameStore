package com.store.gamestore.model.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class GameImagesDTO {
  private MultipartFile storeImage;
  private MultipartFile gamePageImage;
  private MultipartFile collectionImage;
  private MultipartFile[] gameplayImages;
}
