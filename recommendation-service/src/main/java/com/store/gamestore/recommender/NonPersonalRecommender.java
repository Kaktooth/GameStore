package com.store.gamestore.recommender;

import com.store.gamestore.persistence.entity.GameInteraction;
import com.store.gamestore.persistence.entity.InteractionType;
import java.util.List;

public interface NonPersonalRecommender {
  List<GameInteraction> getMostInteractedGames(InteractionType interaction);
}
