package com.store.gamestore.persistence.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "genres")
public class GameCategory {

  @Id
  private Integer id;
  private String name;
}
