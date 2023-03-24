package com.store.gamestore.persistence.entity;

import java.sql.Blob;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "game_files")
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class GameFile extends Domain {

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "version", nullable = false)
  private String version;

  @Column(name = "game_id")
  private UUID gameId;

  @Lob
  @Column(name = "file", nullable = false)
  private Blob file;
}
