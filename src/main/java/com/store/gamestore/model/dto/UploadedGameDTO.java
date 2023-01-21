package com.store.gamestore.model.dto;


import com.store.gamestore.persistence.entity.GamePicture;
import com.store.gamestore.persistence.entity.UploadedGame;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UploadedGameDTO {

  private UploadedGame uploadedGame;

  private GamePicture gamePicture;
}
