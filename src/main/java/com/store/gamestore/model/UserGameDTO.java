package com.store.gamestore.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserGameDTO {
    private User user;
    private Game game;
    private Image image;

    public UserGameDTO(UserGame userGame, Image image) {
        this.setUser(userGame.getUser());
        this.setGame(userGame.getGame());
        this.image = image;
    }
}
