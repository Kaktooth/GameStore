package com.store.gamestore.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor()
@EqualsAndHashCode(callSuper = true)
public class GameImage extends Image {
    private UUID gameId;
    private String pictureType;

    public GameImage(UUID gameId, String pictureType, byte[] imageData) {
        super(imageData);
        this.gameId = gameId;
        this.pictureType = pictureType;
    }
}
