package com.store.gamestore.service.game.search;

import com.store.gamestore.model.UploadedGame;
import com.store.gamestore.repository.CommonRepository;
import com.store.gamestore.service.AbstractService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@Qualifier("searchService")
public class SearchService extends AbstractService<UploadedGame, UUID> implements GameSearcher<UploadedGame> {
    public SearchService(CommonRepository<UploadedGame, UUID> repository) {
        super(repository);
    }

    @Override
    public List<UploadedGame> getAll(UUID id) {
        List<UploadedGame> uploadedGames = super.getAll(id);
        log.info(uploadedGames.toString());
        return uploadedGames;
    }

    @Override
    public List<UploadedGame> getAll() {
        return super.getAll();
    }

    @Override
    public List<UploadedGame> searchGames(String searchString, Integer count) {
        return getAll()
            .stream()
            .filter(upload -> upload.getGame().getGameProfile().getTitle().contains(searchString))
            .limit(count).collect(Collectors.toList());
    }
}
