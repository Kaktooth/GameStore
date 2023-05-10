package com.store.gamestore.model.dto;

import com.store.gamestore.persistence.entity.GamePicture;
import com.store.gamestore.persistence.entity.UserRecommendation;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserRecommendationDTO {

  private UserRecommendation userRecommendation;
  private GamePicture gamePicture;
  private String title;
  private BigDecimal price;

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (!(o instanceof UserRecommendationDTO other)) {
      return false;
    }
    return this.userRecommendation.equals(other.userRecommendation);
  }

  @Override
  public int hashCode() {
    return userRecommendation.hashCode();
  }
}