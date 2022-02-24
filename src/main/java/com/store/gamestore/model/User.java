package com.store.gamestore.model;

import lombok.NonNull;
import lombok.Value;


@Value
public class User {

    @NonNull Integer id;
    @NonNull String username;
    @NonNull String password;
    @NonNull Boolean enabled;
    @NonNull String email;
    String phone;
}

