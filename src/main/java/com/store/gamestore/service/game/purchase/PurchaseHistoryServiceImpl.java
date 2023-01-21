package com.store.gamestore.service.game.purchase;

import com.store.gamestore.persistence.entity.GamePurchase;
import com.store.gamestore.persistence.repository.CommonRepository;
import com.store.gamestore.persistence.repository.game.purchase.PurchaseHistoryRepository;
import com.store.gamestore.service.AbstractService;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class PurchaseHistoryServiceImpl extends AbstractService<GamePurchase, UUID> implements
    PurchaseHistoryService {

  public PurchaseHistoryServiceImpl(CommonRepository<GamePurchase, UUID> repository) {
    super(repository);
  }

  @Override
  public List<GamePurchase> findAllByUserId(UUID userId) {
    return ((PurchaseHistoryRepository) repository).findAllByUserId(userId);
  }
}
