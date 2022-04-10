package com.store.gamestore.service.user.purchase;

import com.store.gamestore.model.entity.UserGame;
import com.store.gamestore.repository.CommonRepository;
import com.store.gamestore.service.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
@Qualifier("userGamesService")
public class UserGamesService extends AbstractService<UserGame, UUID> {
    @Autowired
    public UserGamesService(
        CommonRepository<UserGame, UUID> repository) {
        super(repository);
    }
}
