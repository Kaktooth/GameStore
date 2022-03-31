package com.store.gamestore.service.game.recommended;

import com.store.gamestore.model.UploadedGame;
import com.store.gamestore.repository.CommonRepository;
import com.store.gamestore.service.AbstractService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@Qualifier("gameRecommendationService")
public class GameRecommendationService extends AbstractService<UploadedGame, UUID> implements Recommendations<UploadedGame> {
    public GameRecommendationService(CommonRepository<UploadedGame, UUID> repository) {
        super(repository);
    }

    @Override
    public List<UploadedGame> getAll() {
        return super.getAll();
    }

    @Override
    public List<UploadedGame> getPopularGames(Integer count) {
        List<UploadedGame> uploadedGames = getAll()
            .stream()
            .sorted(Comparator.comparing(upload -> upload.getGame().getGameProfile().getViews()))
            .limit(count)
            .collect(Collectors.toList());
        Collections.reverse(uploadedGames);
        return uploadedGames;
    }

    @Override
    public List<UploadedGame> getBestSellerGames(Integer count) {
        List<UploadedGame> uploadedGames = getAll()
            .stream()
            .sorted(Comparator.comparing(upload -> upload.getGame().getGameProfile().getPurchase()))
            .limit(count)
            .collect(Collectors.toList());
        Collections.reverse(uploadedGames);
        return uploadedGames;
    }

    @Override
    public List<UploadedGame> getMostFavoriteGames(Integer count) {
        List<UploadedGame> uploadedGames = getAll()
            .stream()
            .sorted(Comparator.comparing(upload -> upload.getGame().getGameProfile().getFavorite()))
            .limit(count)
            .collect(Collectors.toList());
        Collections.reverse(uploadedGames);
        return uploadedGames;
    }
}
