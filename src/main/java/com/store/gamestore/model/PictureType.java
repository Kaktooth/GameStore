package com.store.gamestore.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum PictureType {
    STORE("STORE"),
    COLLECTION("COLLECTION"),
    GAMEPAGE("GAMEPAGE"),
    GAMEPLAY("GAMEPLAY");

    @Getter
    private String type;
}
