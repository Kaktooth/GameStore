package com.store.gamestore.model;

import lombok.NonNull;
import lombok.Value;

@Value
public class OperatingSystem {
    @NonNull Integer id;
    @NonNull String name;
}
