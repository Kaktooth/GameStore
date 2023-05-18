package com.store.gamestore.persistence.entity;

import java.util.UUID;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RecommenderType {
  NON_PERSONAL(UUID.fromString("0337ee12-529e-4fb0-a2fe-dc35f59b7552"), "nonPersonal"),
  LDA(UUID.fromString("06078ffa-0fbc-4d67-abfd-c0739a64b9a4"), "LDA");

  private final UUID id;
  private final String name;
}
