package com.store.gamestore.model;

import lombok.NonNull;
import lombok.Value;

@Value
public class Processor {
    @NonNull Integer id;
    @NonNull String name;
}
