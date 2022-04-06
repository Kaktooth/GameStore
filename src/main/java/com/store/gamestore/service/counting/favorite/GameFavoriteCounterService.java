package com.store.gamestore.service.counting.favorite;

import com.store.gamestore.repository.counting.CounterRepository;
import com.store.gamestore.service.counting.AbstractCounterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@Transactional
@Qualifier("gameFavoriteCounterService")
public class GameFavoriteCounterService extends AbstractCounterService<UUID> {
    @Autowired
    public GameFavoriteCounterService(@Qualifier("gameFavoriteCounterRepository")
                                          CounterRepository<UUID> repository) {
        super(repository);
    }
}
