package com.store.gamestore.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;


@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {

    private UUID id;
    private String username;
    private String password;
    private Boolean enabled;
    private String email;
    private String phone;
    private String publicUsername;
    private String resume;
}

