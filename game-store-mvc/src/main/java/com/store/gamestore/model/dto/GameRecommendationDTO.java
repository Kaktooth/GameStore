package com.store.gamestore.model.dto;

import com.store.gamestore.persistence.entity.GamePicture;
import com.store.gamestore.persistence.entity.GameRecommendation;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GameRecommendationDTO {

  private GameRecommendation gameRecommendation;

  private GamePicture gamePicture;

  private String title;

  private BigDecimal price;
}