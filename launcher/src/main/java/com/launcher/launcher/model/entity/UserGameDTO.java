package com.launcher.launcher.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserGameDTO implements Serializable {

    public User user;
    public Game game;
    public GameImage image;

    public User getUser() {
        return user;
    }

    public Game getGame() {
        return game;
    }

    public Image getImage() {
        return image;
    }
}
