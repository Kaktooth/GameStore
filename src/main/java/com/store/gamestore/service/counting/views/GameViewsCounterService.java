package com.store.gamestore.service.counting.views;

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
@Qualifier("gameViewsCounterService")
public class GameViewsCounterService extends AbstractCounterService<UUID> {
    @Autowired
    public GameViewsCounterService(@Qualifier("gameViewsCounterRepository")
                                       CounterRepository<UUID> repository) {
        super(repository);
    }
}