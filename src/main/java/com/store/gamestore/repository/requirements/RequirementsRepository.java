package com.store.gamestore.repository.requirements;

import com.store.gamestore.model.entity.Requirements;
import com.store.gamestore.repository.AbstractRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;

@Slf4j
@Repository
public class RequirementsRepository extends AbstractRepository<Requirements, Integer> {

    private static final String saveRequirements = "INSERT INTO system_requirements(minimal_memory, recommended_memory, " +
        "minimal_storage, recommended_storage, game_profile_id, minimal_processor_id, recommended_processor_id," +
        "minimal_graphic_card_id, recommended_graphic_card_id, minimal_operating_system_id, " +
        "recommended_operating_system_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String getRequirements = "SELECT * FROM system_requirements WHERE game_profile_id = ?";
    private static final String updateRequirements = "UPDATE system_requirements " +
        "SET minimal_memory                  = ?, recommended_memory              = ?," +
        "    minimal_storage                 = ?, recommended_storage             = ?," +
        "    minimal_processor_id            = ?, recommended_processor_id        = ?," +
        "    minimal_graphic_card_id         = ?, recommended_graphic_card_id     = ?," +
        "    minimal_operating_system_id     = ?, recommended_operating_system_id = ?" +
        "    WHERE game_profile_id = ?";
    private static final String deleteRequirements = "DELETE FROM system_requirements WHERE id = ?";

    public RequirementsRepository(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public Requirements save(Requirements requirements) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(saveRequirements, new String[]{"game_profile_id"});
            ps.setInt(1, requirements.getMinimalMemory());
            ps.setInt(2, requirements.getRecommendedMemory());
            ps.setInt(3, requirements.getMinimalStorage());
            ps.setInt(4, requirements.getRecommendedStorage());
            ps.setInt(5, requirements.getGameProfileId());
            ps.setInt(6, requirements.getMinimalProcessorId());
            ps.setInt(7, requirements.getRecommendedProcessorId());
            ps.setInt(8, requirements.getMinimalGraphicCardId());
            ps.setInt(9, requirements.getRecommendedGraphicCardId());
            ps.setInt(10, requirements.getMinimalOperatingSystemId());
            ps.setInt(11, requirements.getRecommendedOperatingSystemId());
            return ps;
        }, keyHolder);

        Integer entityId = (Integer) keyHolder.getKey();

        return get(entityId);
    }

    @Override
    public Requirements get(Integer id) {
        return jdbcTemplate.queryForObject(getRequirements, new BeanPropertyRowMapper<>(Requirements.class), id);
    }

    @Override
    public void update(Requirements requirements) {
        jdbcTemplate.update(updateRequirements,
            requirements.getMinimalMemory(), requirements.getRecommendedMemory(),
            requirements.getMinimalStorage(), requirements.getRecommendedStorage(),
            requirements.getMinimalProcessorId(), requirements.getRecommendedProcessorId(),
            requirements.getMinimalGraphicCardId(), requirements.getRecommendedGraphicCardId(),
            requirements.getMinimalOperatingSystemId(), requirements.getRecommendedOperatingSystemId(),
            requirements.getGameProfileId());
    }

    @Override
    public void delete(Integer id) {
        jdbcTemplate.update(deleteRequirements, id);
    }
}
