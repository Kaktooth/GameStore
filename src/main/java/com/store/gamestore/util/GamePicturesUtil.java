package com.store.gamestore.util;

import com.store.gamestore.model.GameImage;
import com.store.gamestore.model.PictureType;

import java.util.ArrayList;
import java.util.List;

public class GamePicturesUtil {
    public static GameImage getGamePicture(List<GameImage> gameImages, PictureType pictureType) {
        GameImage gamePicture = null;
        for (GameImage gameImage : gameImages) {
            if (gameImage.getPictureType().equals(pictureType.getType())) {
                gamePicture = gameImage;
                break;
            }
        }
        return gamePicture;
    }

    public static List<GameImage> getGameplayPictures(List<GameImage> gameImages) {
        List<GameImage> gamePictures = new ArrayList<>();
        for (GameImage gameImage : gameImages) {
            if (gameImage.getPictureType().equals(PictureType.GAMEPLAY.getType())) {
                gamePictures.add(gameImage);
            }
        }
        return gamePictures;
    }
}
