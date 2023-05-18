package com.store.gamestore.persistence.entity;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum InteractionType {
  BOUGHT(0.9f),
  DOWNLOADED(0.8f),
  FAVORITE(0.7f),
  VISITED(0.1f),
  IGNORED(0);
  private final double weight;
}
