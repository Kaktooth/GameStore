package com.store.gamestore.persistence.entity;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "user-interactions")
public class UserInteraction {

  @MongoId
  private String id;
  private String userId;
  private String gameId;
  private InteractionType interactionType;
  private LocalDateTime date;
  private Boolean recommended;
}
