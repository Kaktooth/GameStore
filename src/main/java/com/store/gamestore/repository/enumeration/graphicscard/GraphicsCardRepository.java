package com.store.gamestore.repository.enumeration.graphicscard;

import com.store.gamestore.model.entity.GraphicsCard;
import com.store.gamestore.model.entity.GraphicsCardMapper;
import com.store.gamestore.repository.enumeration.AbstractEnumerationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

@Slf4j
@Repository
public class GraphicsCardRepository extends AbstractEnumerationRepository<GraphicsCard, Integer> {
    private static final String getAllGraphicCards = "SELECT * FROM graphics_cards ORDER BY name";
    private static final String getGraphicCard = "SELECT * FROM graphics_cards WHERE id = ?";

    public GraphicsCardRepository(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public GraphicsCard get(Integer id) {
        return jdbcTemplate.queryForObject(getGraphicCard, new BeanPropertyRowMapper<>(GraphicsCard.class), id);
    }

    @Override
    public List<GraphicsCard> getAll() {
        return new ArrayList<>(new LinkedHashSet<>(jdbcTemplate.query(getAllGraphicCards, new GraphicsCardMapper())));
    }
}
