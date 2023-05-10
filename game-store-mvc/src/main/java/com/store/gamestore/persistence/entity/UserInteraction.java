package com.store.gamestore.persistence.entity;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInteraction {

  private String id;
  private String userId;
  private String gameId;
  private InteractionType interactionType;
  private LocalDateTime date;
  private Boolean recommended;
  private String recommenderName;
}
