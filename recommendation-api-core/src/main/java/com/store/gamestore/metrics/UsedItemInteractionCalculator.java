package com.store.gamestore.metrics;

import com.store.gamestore.persistence.entity.InteractionType;
import java.util.UUID;

public interface UsedItemInteractionCalculator {

  Integer getUsedGamesInteractions(UUID userId, InteractionType interaction, boolean recommended);

  Integer getNotUsedGamesInteractions(UUID userId, InteractionType interaction, boolean recommended);
}
