package com.store.gamestore.repository.enumeration.processor;

import com.store.gamestore.model.entity.Processor;
import com.store.gamestore.model.entity.ProcessorMapper;
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
public class ProcessorRepository extends AbstractEnumerationRepository<Processor, Integer> {
    private static final String getAllProcessors = "SELECT * FROM processors ORDER BY name";
    private static final String getProcessor = "SELECT * FROM processors WHERE id = ?";

    public ProcessorRepository(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public Processor get(Integer id) {
        return jdbcTemplate.queryForObject(getProcessor, new BeanPropertyRowMapper<>(Processor.class), id);
    }

    @Override
    public List<Processor> getAll() {
        return new ArrayList<>(new LinkedHashSet<>(jdbcTemplate.query(getAllProcessors, new ProcessorMapper())));
    }
}
