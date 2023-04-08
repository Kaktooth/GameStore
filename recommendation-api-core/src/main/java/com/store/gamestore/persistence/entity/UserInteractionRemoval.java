package com.store.gamestore.persistence.entity;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInteractionRemoval {

  private UUID userId;
  private UUID gameId;
  private InteractionType interactionType;
}
