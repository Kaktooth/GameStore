package com.store.gamestore.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.Value;

import java.util.UUID;


@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private UUID id;
    private String username;
    private String profileUsername;
    private String password;
    private Boolean enabled;
    private String email;
    private String phone;
}

