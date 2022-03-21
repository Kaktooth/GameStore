package com.store.gamestore.model;

import lombok.NonNull;
import lombok.Value;

@Value
public class GraphicsCard {
    @NonNull Integer id;
    @NonNull String name;
}
