package com.store.gamestore.model.entity;

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
public class UploadedGameDTO extends UploadedGame {
    private GameImage gameImage;

    public UploadedGameDTO(UploadedGame uploadedGame, GameImage gameImage) {
        this.setGame(uploadedGame.getGame());
        this.setUser(uploadedGame.getUser());
        this.setGameImage(gameImage);
    }
}
