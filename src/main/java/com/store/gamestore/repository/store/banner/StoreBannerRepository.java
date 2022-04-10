package com.store.gamestore.repository.store.banner;

import com.store.gamestore.model.entity.StoreBannerItem;
import com.store.gamestore.model.entity.StoreBannerItemMapper;
import com.store.gamestore.repository.AbstractRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.io.ByteArrayInputStream;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.UUID;

@Slf4j
@Repository
public class StoreBannerRepository extends AbstractRepository<StoreBannerItem, UUID> {

    private static final String saveBanner = "INSERT INTO store_banner VALUES (?, ?, ?, ?)";
    private static final String saveImage = "INSERT INTO images (image) VALUES (?)";
    private static final String getBanner = "SELECT * FROM store_banner " +
        "INNER JOIN images i on store_banner.image_id = i.image_id " +
        "WHERE game_id = ?;";
    private static final String getBanners = "SELECT * FROM store_banner " +
        "INNER JOIN images i on store_banner.image_id = i.image_id ";

    public StoreBannerRepository(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public StoreBannerItem save(StoreBannerItem banner) {

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(saveImage, new String[]{"image_id"});
            ps.setBinaryStream(1, new ByteArrayInputStream(banner.getImageData()));
            return ps;
        }, keyHolder);
        Integer imageId = (Integer) keyHolder.getKey();
        jdbcTemplate.update(saveBanner, banner.getUserId(), banner.getGameId(),
            imageId, banner.getDescription());
        return banner;
    }

    @Override
    public List<StoreBannerItem> getAll() {
        return jdbcTemplate.query(getBanners, new StoreBannerItemMapper());
    }

    @Override
    public StoreBannerItem get(UUID gameId) {
        return jdbcTemplate.query(getBanner, new StoreBannerItemMapper(), gameId).iterator().next();
    }
}
