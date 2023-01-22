package com.store.gamestore.persistence.entity;

import java.sql.Blob;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

@Data
@Entity
@Table(name = "game_files")
@AllArgsConstructor
@NoArgsConstructor
public class GameBlob extends Domain {

  @Lob
  @Type(type = "org.hibernate.type.BinaryType")
  @Column(name = "file")
  private Blob blob;
  @Column(name = "version")
  private String version;
  @Column(name = "name")
  private String name;
}
