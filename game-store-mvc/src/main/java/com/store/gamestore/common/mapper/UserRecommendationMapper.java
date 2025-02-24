package com.store.gamestore.common.mapper;

import com.store.gamestore.model.dto.UserRecommendationDTO;
import com.store.gamestore.persistence.entity.UserRecommendation;
import java.util.List;

public interface UserRecommendationMapper {

  UserRecommendationDTO sourceToDestination(UserRecommendation userRecommendation);

  List<UserRecommendationDTO> sourceToDestination(List<UserRecommendation> userRecommendations);
}
