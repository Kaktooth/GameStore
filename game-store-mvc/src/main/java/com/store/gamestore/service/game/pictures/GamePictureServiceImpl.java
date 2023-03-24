package com.store.gamestore.service.game.pictures;

import com.store.gamestore.persistence.entity.GamePicture;
import com.store.gamestore.persistence.repository.game.pictures.GamePictureRepository;
import com.store.gamestore.service.AbstractService;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class GamePictureServiceImpl extends AbstractService<GamePicture, UUID>
    implements GamePictureService {

  public GamePictureServiceImpl(GamePictureRepository repository) {
    super(repository);
  }

  @Override
  public GamePicture findGamePictureByGameIdAndPictureTypeId(UUID gameId, Integer typeId) {
    return ((GamePictureRepository) repository).findGamePictureByGameIdAndPictureTypeId(gameId,
        typeId);
  }

  @Override
  public List<GamePicture> findGamePictureByGameIdsAndPictureTypeId(Iterable<UUID> gameIds,
      Integer typeId) {
    List<GamePicture> gamePictures = new ArrayList<>();
    for (var gameId : gameIds) {
      gamePictures.add(findGamePictureByGameIdAndPictureTypeId(gameId, typeId));
    }
    return gamePictures;
  }

  @Override
  public List<GamePicture> findGameplayPicturesByGameId(UUID gameId) {
    return ((GamePictureRepository)repository).findGameplayPicturesByGameId(gameId);
  }

}
