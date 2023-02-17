package com.store.gamestore.model.dto;

import com.store.gamestore.persistence.entity.Image;
import lombok.Data;

@Data
public class GameplayImagesDTO {

  private Image[] gameplayImages;
}