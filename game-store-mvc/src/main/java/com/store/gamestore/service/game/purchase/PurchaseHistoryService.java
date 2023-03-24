package com.store.gamestore.service.game.purchase;

import com.store.gamestore.persistence.entity.GamePurchase;
import com.store.gamestore.service.CommonService;
import java.util.List;
import java.util.UUID;

public interface PurchaseHistoryService extends CommonService<GamePurchase, UUID> {

  List<GamePurchase> findAllByUserId(UUID userId);
}
