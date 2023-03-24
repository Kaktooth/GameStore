package com.store.gamestore.persistence.entity;

import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "games")
public class GameTitleMetadata {

  @Id
  private UUID id;
  private String title;
}
