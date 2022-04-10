package com.store.gamestore.repository.user.pictures;


import com.store.gamestore.model.entity.UserImage;
import com.store.gamestore.model.entity.UserImageMapper;
import com.store.gamestore.repository.AbstractRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.io.ByteArrayInputStream;
import java.sql.PreparedStatement;
import java.util.NoSuchElementException;
import java.util.UUID;

@Slf4j
@Repository
public class UserPicturesRepository extends AbstractRepository<UserImage, UUID> {
    private static final String saveUserPicture = "INSERT INTO user_pictures(user_id, image_id) VALUES (?, ?)";
    private static final String saveImage = "INSERT INTO images (image) VALUES (?)";
    private static final String getImage = "SELECT * FROM user_pictures\n" +
        "INNER JOIN images i on user_pictures.image_id = i.image_id\n" +
        "WHERE user_id = ?;";
    private static final String deleteImage = "DELETE FROM user_pictures WHERE user_id = ?";

    public UserPicturesRepository(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public UserImage save(UserImage userImage) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(saveImage, new String[]{"image_id"});
            ps.setBinaryStream(1, new ByteArrayInputStream(userImage.getImageData()));
            return ps;
        }, keyHolder);

        Integer imageId = (Integer) keyHolder.getKey();
        jdbcTemplate.update(saveUserPicture, userImage.getUserId(), imageId);

        return userImage;
    }

    @Override
    public UserImage get(UUID id) {
        try {
            return jdbcTemplate.query(getImage, new UserImageMapper(), id).iterator().next();
        } catch (NoSuchElementException ex) {
            return null;
        }
    }

    @Override
    public void delete(UUID id) {
        jdbcTemplate.update(deleteImage, id);
    }

    @Override
    public void update(UserImage userImage) {
        delete(userImage.getUserId());
        save(userImage);
    }
}