package com.store.gamestore.service.recommendation;

import com.store.gamestore.persistence.entity.UserRecommendation;
import java.util.List;

public interface UserRecommendationService {

  List<UserRecommendation> getRecommendations();

  List<UserRecommendation> getBestRecommendations();
}
