package com.store.gamestore.persistence.repository.game.purchase;

import com.store.gamestore.persistence.entity.GamePurchase;
import com.store.gamestore.persistence.repository.CommonRepository;
import java.util.List;
import java.util.UUID;

public interface PurchaseHistoryRepository extends CommonRepository<GamePurchase, UUID> {

  List<GamePurchase> findAllByUserId(UUID userId);
}