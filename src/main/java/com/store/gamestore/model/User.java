package com.store.gamestore.model;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.sql.Blob;
import java.util.UUID;


@Value
@Builder
public class User {

    @NonNull UUID id;
    @NonNull String username;
    @NonNull String profileUsername;
    Blob image;
    @NonNull String password;
    @NonNull Boolean enabled;
    @NonNull String email;
    String phone;
}

