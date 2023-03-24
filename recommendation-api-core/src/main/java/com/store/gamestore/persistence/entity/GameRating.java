package com.store.gamestore.persistence.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Data
@AllArgsConstructor
@Document(collection = "user-ratings")
public class GameRating implements Serializable {

  @MongoId
  private UUID id;
  private UUID userId;
  private UUID gameId;
  private Double rating;
  private LocalDateTime dateTime;
}
