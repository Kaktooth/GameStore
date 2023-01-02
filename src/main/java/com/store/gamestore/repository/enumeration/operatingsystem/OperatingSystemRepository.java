package com.store.gamestore.repository.enumeration.operatingsystem;

import com.store.gamestore.model.entity.OperatingSystem;
import com.store.gamestore.model.entity.OperatingSystemMapper;
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
public class OperatingSystemRepository extends AbstractEnumerationRepository<OperatingSystem, Integer> {
    private static final String getAllOS = "SELECT * FROM operating_systems ORDER BY name";
    private static final String getOS = "SELECT * FROM operating_systems WHERE id = ?";

    public OperatingSystemRepository(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public OperatingSystem get(Integer id) {
        return jdbcTemplate.queryForObject(getOS, new BeanPropertyRowMapper<>(OperatingSystem.class), id);
    }

    @Override
    public List<OperatingSystem> getAll() {
        return new ArrayList<>(new LinkedHashSet<>(jdbcTemplate.query(getAllOS, new OperatingSystemMapper())));
    }
}
