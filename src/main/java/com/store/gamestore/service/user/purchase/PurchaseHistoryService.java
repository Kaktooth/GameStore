package com.store.gamestore.service.user.purchase;

import com.store.gamestore.model.entity.GamePurchase;
import com.store.gamestore.repository.CommonRepository;
import com.store.gamestore.service.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class PurchaseHistoryService extends AbstractService<GamePurchase, UUID> {
    @Autowired
    public PurchaseHistoryService(CommonRepository<GamePurchase, UUID> repository) {
        super(repository);
    }
}
