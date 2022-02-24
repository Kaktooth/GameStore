package com.store.gamestore.model;

import lombok.NonNull;
import lombok.Value;

@Value
public class Game {

    @NonNull Integer id;
    @NonNull Integer objectId;
}
