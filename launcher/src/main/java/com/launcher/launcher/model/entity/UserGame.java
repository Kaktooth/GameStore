package com.launcher.launcher.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserGame {
    public User user;
    public Game game;

    public Game getGame() {
        return game;
    }

    public User getUser() {
        return user;
    }
}
