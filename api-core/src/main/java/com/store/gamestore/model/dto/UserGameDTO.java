package com.store.gamestore.model.dto;


import com.store.gamestore.persistence.entity.Game;
import com.store.gamestore.persistence.entity.Image;
import com.store.gamestore.persistence.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserGameDTO {

  private User user;
  private Game game;
  private Image image;
}
