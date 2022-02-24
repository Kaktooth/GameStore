package com.store.gamestore.service;

import com.store.gamestore.repository.CommonRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Slf4j
@Service
@Transactional
public class GameService implements CommonService<MultipartFile, Integer> {

    private final CommonRepository<MultipartFile, Integer> gameRepository;

    @Autowired
    public GameService(CommonRepository<MultipartFile, Integer> gameRepository){
        this.gameRepository = gameRepository;
    }

    @Override
    public void save(MultipartFile gameFile) {
        gameRepository.save(gameFile);
    }

    @Override
    public MultipartFile get(Integer id) {
        return null;
    }

    @Override
    public void update(MultipartFile gameFile) {

    }

    @Override
    public void delete(Integer id) {

    }
}
