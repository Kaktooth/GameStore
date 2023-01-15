package com.store.gamestore.model.dto;


import com.store.gamestore.persistence.entity.Image;
import java.io.Serializable;
import java.util.UUID;
import lombok.Data;

@Data
public class GamePictureDTO implements Serializable {

  private UUID gameId;

  private Integer pictureTypeId;

  private Image image;

}
