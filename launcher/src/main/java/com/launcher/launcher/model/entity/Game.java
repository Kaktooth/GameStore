package com.launcher.launcher.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Game {
    public UUID id;
    public Set<GameFile> gameFiles;
    public GameProfile gameProfile;
    public GameGenre genre;

    public UUID getId() {
        return id;
    }

    public GameProfile getGameProfile() {
        return gameProfile;
    }

    public GameGenre getGenre() {
        return genre;
    }
}
