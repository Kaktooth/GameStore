package com.store.gamestore.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Set;
import java.util.UUID;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Game implements Serializable {
    private UUID id;
    private Set<GameFile> gameFiles;
    private GameProfile gameProfile;
    private GameGenre genre;
}
