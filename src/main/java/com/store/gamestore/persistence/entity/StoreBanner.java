package com.store.gamestore.persistence.entity;

import java.io.Serializable;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "store_banner")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class StoreBanner extends Domain implements Serializable {

  @Column(name = "user_id", nullable = false)
  private UUID userId;

  @Column(name = "game_id", nullable = false)
  private UUID gameId;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "image_id")
  private Image image;

  @Column(name = "description", nullable = false)
  private String description;

}
