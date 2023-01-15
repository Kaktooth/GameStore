package com.store.gamestore.persistence.entity;

import java.sql.Blob;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

@Data
@Entity
@Table(name = "game_files")
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class GameFile extends Domain {

  @Column(name = "file_name", nullable = false)
  private String fileName;

  @Lob
  @Type(type = "org.hibernate.type.BinaryType")
  @Column(name = "file", nullable = false)
  private byte[] file;

  @Column(name = "version", nullable = false)
  private String version;

  @Column(name = "game_id")
  private UUID gameId;

}
