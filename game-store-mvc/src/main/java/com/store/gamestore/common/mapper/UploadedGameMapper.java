package com.store.gamestore.common.mapper;

import com.store.gamestore.model.dto.UploadedGameDTO;
import com.store.gamestore.persistence.entity.UploadedGame;
import java.util.List;

public interface UploadedGameMapper {

  UploadedGameDTO sourceToDestination(UploadedGame uploadedGame);

  List<UploadedGameDTO> sourceToDestination(List<UploadedGame> uploadedGames);
}
