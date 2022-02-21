package com.store.gamestore.model;

import lombok.Data;
import lombok.NonNull;

import java.sql.Blob;

@Data
public class Game {

    @NonNull
    private Integer id;

    @NonNull
    private Integer objectId;

    @NonNull
    private String name;

    @NonNull
    private Blob blob;
}
