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
public class UploadedGameDto extends UploadedGame {
    private GameImage gameImage;

    public UploadedGameDto(UploadedGame uploadedGame, GameImage gameImage) {
        this.setGame(uploadedGame.getGame());
        this.setUser(uploadedGame.getUser());
        this.setGameImage(gameImage);
    }
}
