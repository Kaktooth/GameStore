package com.launcher.launcher.model.entity;

import java.util.Set;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Game {

  private UUID id;
  private Set<GameFile> gameFiles;
  private GameProfile gameProfile;
  private GameGenre genre;
}
