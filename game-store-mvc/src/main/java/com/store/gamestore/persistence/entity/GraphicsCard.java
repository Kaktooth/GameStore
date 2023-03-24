package com.store.gamestore.persistence.entity;

import java.io.Serial;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "graphics_cards")
public class GraphicsCard implements Serializable {

  @Serial
  private static final long serialVersionUID = 2405172041950251807L;

  @Id
  @Column(name = "id", nullable = false)
  private Integer id;

  @Column(name = "name", nullable = false)
  private String name;

}
