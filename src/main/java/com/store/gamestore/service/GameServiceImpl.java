package com.store.gamestore.service;

import com.store.gamestore.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;

@Service
@Transactional
public class GameServiceImpl implements GameService<File, Integer> {

    private final GameRepository<File, Integer> gameRepository;

    @Autowired
    public GameServiceImpl(GameRepository<File, Integer> gameRepository){
        this.gameRepository = gameRepository;
    }

    @Override
    public void save(File gameFile) {
        gameRepository.save(gameFile);
    }

    @Override
    public File get(Integer id) {
        return null;
    }

    @Override
    public void update(File gameFile) {

    }

    @Override
    public void delete(Integer id) {

    }
}
