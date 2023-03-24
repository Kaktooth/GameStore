package com.store.gamestore.metrics;

import com.store.gamestore.persistence.entity.InteractionType;
import java.util.UUID;

public interface UsedItemInteractionCalculator {

  Integer getUsedItemInteractions(UUID userId, InteractionType interaction, boolean recommended);

  Integer getNotUsedItemInteractions(UUID userId, InteractionType interaction, boolean recommended);
}
