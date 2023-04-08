package com.store.gamestore.model.dto;

import com.store.gamestore.persistence.entity.GamePicture;
import com.store.gamestore.persistence.entity.UserRecommendation;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserRecommendationDTO {

  private UserRecommendation userRecommendation;

  private GamePicture gamePicture;
}