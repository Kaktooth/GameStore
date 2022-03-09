package com.store.gamestore.service.game;

import com.store.gamestore.repository.CommonRepository;
import com.store.gamestore.service.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
public class GameService extends AbstractService<MultipartFile, Integer> {

    public GameService(CommonRepository<MultipartFile, Integer> gameRepository) {
        super(gameRepository);
    }

    @Override
    public void save(MultipartFile gameFile) {
        repository.save(gameFile);
    }

    @Override
    public MultipartFile get(Integer id) {
        return repository.get(id);
    }

    @Override
    public void update(MultipartFile gameFile) {
        repository.update(gameFile);
    }

    @Override
    public void delete(Integer id) {
        repository.delete(id);
    }
}
