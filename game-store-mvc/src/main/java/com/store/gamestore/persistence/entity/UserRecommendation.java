package com.store.gamestore.persistence.entity;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserRecommendation implements Serializable {

  @Serial
  private static final long serialVersionUID = -6708530972415161759L;

  private UUID id;
  private Double predictedRating;
  private LocalDateTime lastRecommendedDate;
  private UUID userId;
  private UUID gameId;
  private Integer topicId;
  private String recommenderName;

  @Override public boolean equals(Object o) {
    if (o == this)
      return true;
    if (!(o instanceof UserRecommendation))
      return false;
    UserRecommendation other = (UserRecommendation)o;
    return this.userId.equals(other.userId) && this.gameId.equals(other.gameId);
  }

  @Override public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = (result*PRIME) + (this.userId == null ? 43 : this.userId.hashCode());
    result = (result*PRIME) + (this.gameId == null ? 43 : this.gameId.hashCode());
    return result;
  }
}
