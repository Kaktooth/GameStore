package com.store.gamestore.service.search;

import com.store.gamestore.model.dto.GameDTO;
import com.store.gamestore.persistence.entity.GamePictureType;
import com.store.gamestore.persistence.repository.game.GameRepository;
import com.store.gamestore.persistence.repository.game.pictures.GamePictureRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class GameSearchService implements SearchService<GameDTO> {

  private final GameRepository gameRepository;

  private final GamePictureRepository gamePictureRepository;

  @Override
  @Transactional(readOnly = true)
  public List<GameDTO> searchGames(String gameTitle, Integer count) {
    final var gamesList = gameRepository.fullSearchByGameTitle(gameTitle);
    gameRepository.fullSearchByGameTitle(gameTitle);
    return gamesList
        .stream()
        .limit(count)
        .map(game -> new GameDTO(game,
            gamePictureRepository.findGamePictureByGameIdAndPictureTypeId(game.getId(),
                GamePictureType.COLLECTION.ordinal())))
        .toList();
  }
}
