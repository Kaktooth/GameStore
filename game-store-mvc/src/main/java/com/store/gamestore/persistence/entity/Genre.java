package com.store.gamestore.persistence.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "genres")
public class Genre implements Serializable {

  @Id
  private Integer id;
  private String name;

  @Override
  public String toString() {
    return name;
  }
}
