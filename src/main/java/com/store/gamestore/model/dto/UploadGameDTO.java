package com.store.gamestore.model.dto;

import java.math.BigDecimal;
import java.util.List;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UploadGameDTO {

  private BigDecimal price;
  private String title;
  private String developer;
  private String publisher;
  private String description;
  private String smallDescription;
  private Integer minMemory;
  private Integer minStorage;
  private Integer recMemory;
  private Integer recStorage;
  private Integer minProcessorId;
  private Integer minGraphicCardId;
  private Integer minOSId;
  private Integer recProcessorId;
  private Integer recGraphicCardId;
  private Integer recOSId;
  private List<Integer> genres;
  private String version;
  private GameImagesDTO gameImages;
  private MultipartFile file;
}
