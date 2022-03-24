package com.store.gamestore.repository.requirements;

import com.store.gamestore.model.ComputerComponent;
import com.store.gamestore.model.ComputerComponentMapper;
import com.store.gamestore.model.Requirements;
import com.store.gamestore.repository.AbstractRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Slf4j
@Repository
public class RequirementsRepositoryImpl extends AbstractRepository<Requirements, Integer> implements RequirementsRepository {
    private static final String getAllProcessors = "SELECT * FROM processors";
    private static final String getProcessorId = "SELECT * FROM processors WHERE name = ?";

    private static final String getAllGraphicCards = "SELECT * FROM graphics_cards";
    private static final String getGraphicCardId = "SELECT * FROM graphics_cards WHERE name = ?";

    private static final String getAllOS = "SELECT * FROM operating_systems";
    private static final String getOSId = "SELECT * FROM operating_systems WHERE name = ?";

    private static final String saveRequirements = "INSERT INTO system_requirements(minimal_ram, recommended_ram," +
        " minimal_disk_free_memory, recommended_disk_free_memory, game_profile_id, minimal_processor," +
        " recommended_processor, minimal_graphics_card, recommended_graphics_card, minimal_operating_system," +
        " recommended_operating_system) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String getRequirements = "SELECT * FROM system_requirements WHERE id = ?";

    public RequirementsRepositoryImpl(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public Set<ComputerComponent> getProcessorNames() {
        return new HashSet<>(jdbcTemplate.query(getAllProcessors, new ComputerComponentMapper()));
    }

    @Override
    public Integer getProcessorId(String processor) {
        log.info("Convert: " + processor);
        return Objects.requireNonNull(jdbcTemplate.queryForObject(getProcessorId, ComputerComponent.class, processor)).getId();
    }

    @Override
    public Set<ComputerComponent> getGraphicCardNames() {
        return new HashSet<>(jdbcTemplate.query(getAllGraphicCards, new ComputerComponentMapper()));
    }

    @Override
    public Integer getGraphicCardId(String graphicsCard) {
        log.info("Convert: " + graphicsCard);
        return Objects.requireNonNull(jdbcTemplate.queryForObject(getGraphicCardId, ComputerComponent.class, graphicsCard)).getId();
    }

    @Override
    public Set<ComputerComponent> getOSNames() {
        return new HashSet<>(jdbcTemplate.query(getAllOS, new ComputerComponentMapper()));
    }

    @Override
    public Integer getOSId(String os) {
        log.info("Convert: " + os);
        return Objects.requireNonNull(jdbcTemplate.queryForObject(getOSId, ComputerComponent.class, os)).getId();
    }

    @Override
    public Requirements save(Requirements requirements) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(saveRequirements, new String[]{"id"});
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

    }

    @Override
    public void delete(Integer id) {
        super.delete(id);
    }
}
