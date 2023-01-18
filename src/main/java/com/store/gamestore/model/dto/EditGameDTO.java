package com.store.gamestore.model.dto;

import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EditGameDTO {

  private BigDecimal price;
  private String title;
  private String developer;
  private String publisher;
  private String description;
  private String smallDescription;
  private String release;
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
}
