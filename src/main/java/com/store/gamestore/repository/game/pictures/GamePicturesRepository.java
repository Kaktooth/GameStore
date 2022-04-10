package com.store.gamestore.repository.game.pictures;

import com.store.gamestore.model.entity.GameImage;
import com.store.gamestore.model.entity.GameImageMapper;
import com.store.gamestore.repository.AbstractRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.io.ByteArrayInputStream;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.UUID;

@Slf4j
@Repository
public class GamePicturesRepository extends AbstractRepository<GameImage, UUID> {
    private static final String saveGamePicture = "INSERT INTO game_pictures (game_id, picture_type_id, image_id) " +
        "VALUES (?, ?, ?)";

    private static final String getPictureTypeId = "SELECT type_id FROM game_picture_types WHERE game_picture_type = ?";

    private static final String saveImage = "INSERT INTO images (image) VALUES (?)";

    private static final String getImages = "SELECT * FROM game_pictures " +
        "INNER JOIN images i on game_pictures.image_id = i.image_id " +
        "INNER JOIN game_picture_types gpt on game_pictures.picture_type_id = gpt.type_id\n" +
        "WHERE game_id = ?;";

    private static final String deleteImage = "DELETE FROM game_pictures WHERE game_id = ?";

    public GamePicturesRepository(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public GameImage save(GameImage image) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(saveImage, new String[]{"image_id"});
            ps.setBinaryStream(1, new ByteArrayInputStream(image.getImageData()));
            return ps;
        }, keyHolder);

        Integer imageId = (Integer) keyHolder.getKey();
        Integer pictureTypeId = jdbcTemplate.queryForObject(getPictureTypeId, Integer.class, image.getPictureType());
        jdbcTemplate.update(saveGamePicture, image.getGameId(), pictureTypeId, imageId);

        return image;
    }

    @Override
    public List<GameImage> getAll(UUID id) {
        return new ArrayList<>(new LinkedHashSet<>(jdbcTemplate.query(getImages, new GameImageMapper(), id)));
    }

    @Override
    public void delete(UUID id) {
        jdbcTemplate.update(deleteImage, id);
    }
}
