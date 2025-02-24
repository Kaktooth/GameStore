package com.store.gamestore.common.mapper;

import com.store.gamestore.model.dto.UserGameDTO;
import com.store.gamestore.persistence.entity.UserGame;
import java.util.List;

public interface UserGameMapper {

  UserGameDTO sourceToDestination(UserGame userGame);

  List<UserGameDTO> sourceToDestination(List<UserGame> userGames);
}
