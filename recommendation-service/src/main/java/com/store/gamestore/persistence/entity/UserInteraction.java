package com.store.gamestore.persistence.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "user-interactions")
public class UserInteraction implements Serializable {

  @MongoId
  private UUID id;
  private UUID userId;
  private UUID gameId;
  private InteractionType interactionType;
  private LocalDateTime date;
  private Boolean recommended;
  private String recommenderName;
}
