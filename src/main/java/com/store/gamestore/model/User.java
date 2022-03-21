package com.store.gamestore.model;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.util.UUID;


@Value
@Builder
public class User {

    @NonNull UUID id;
    @NonNull String username;
    @NonNull String profileUsername;
    @NonNull String password;
    @NonNull Boolean enabled;
    @NonNull String email;
    String phone;
}

