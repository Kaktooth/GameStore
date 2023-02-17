package com.store.gamestore.persistence.entity;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "game_pictures")
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class GamePicture extends Domain {

  @Column(name = "game_id")
  private UUID gameId;

  @Column(name = "picture_type_id")
  private Integer pictureTypeId;

  @OneToOne
  @JoinColumn(name = "image_id")
  private Image image;

}
