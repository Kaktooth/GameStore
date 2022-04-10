package com.launcher.launcher.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;


@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    public UUID id;
    public String username;
    public String password;
    public Boolean enabled;
    public String email;
    public String phone;
    public String publicUsername;
    public String resume;
}

