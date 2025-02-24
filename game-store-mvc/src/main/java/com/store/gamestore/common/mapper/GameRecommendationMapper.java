package com.store.gamestore.common.mapper;

import com.store.gamestore.model.dto.GameRecommendationDTO;
import com.store.gamestore.model.dto.UserRecommendationDTO;
import com.store.gamestore.persistence.entity.GameRecommendation;
import com.store.gamestore.persistence.entity.UserRecommendation;
import java.util.List;

public interface GameRecommendationMapper {

  GameRecommendationDTO sourceToDestination(GameRecommendation gameRecommendation);

  List<GameRecommendationDTO> sourceToDestination(List<GameRecommendation> gameRecommendations);
}
