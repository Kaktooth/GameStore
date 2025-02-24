package com.store.gamestore.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInteractionRemoval {

  private String userId;
  private String gameId;
  private InteractionType interactionType;
}
