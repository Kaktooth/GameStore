package com.store.gamestore.persistence.entity;

import java.io.Serializable;
import java.util.UUID;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class GameInteraction implements Serializable {

  private static final long serialVersionUID = -1872989251086517457L;

  final UUID gameId;
  final Integer gameInteractions;
}
