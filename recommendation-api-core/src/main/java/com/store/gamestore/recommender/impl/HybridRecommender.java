package com.store.gamestore.recommender.impl;

import com.store.gamestore.persistence.repository.UserProfileRepository;
import com.store.gamestore.recommender.Recommender;
import java.util.List;
import lombok.Builder;
import lombok.Singular;
import org.springframework.stereotype.Component;

@Builder
@Component
public class HybridRecommender implements Recommender {

  @Singular
  private List<Recommender> recommenders;

  private final UserProfileRepository userProfileRepository;

  //TODO normalize all recommenders weights
  @Override
  public void recommend() {

  }
}
