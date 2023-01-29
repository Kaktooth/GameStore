package com.store.gamestore.model.dto;


import java.util.UUID;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class StoreBannerDTO {

  private UUID userId;

  private UUID gameId;

  private MultipartFile imageFile;

  private String description;

}
