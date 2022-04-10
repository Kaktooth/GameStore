package com.store.gamestore.model.entity;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RequirementsMapper implements RowMapper<Requirements> {
    public Requirements mapRow(ResultSet rs, int rowNum) throws SQLException {
        Requirements requirements = new Requirements();
        requirements.setGameProfileId(rs.getInt("game_profile_id"));
        requirements.setMinimalMemory(rs.getInt("minimal_ram"));
        requirements.setRecommendedMemory(rs.getInt("recommended_ram"));
        requirements.setMinimalStorage(rs.getInt("minimal_storage"));
        requirements.setRecommendedStorage(rs.getInt("recommended_storage"));
        requirements.setMinimalProcessorId(rs.getInt("minimal_processor"));
        requirements.setRecommendedProcessorId(rs.getInt("recommended_processor"));
        requirements.setMinimalGraphicCardId(rs.getInt("minimal_graphics_card"));
        requirements.setRecommendedGraphicCardId(rs.getInt("recommended_graphics_card"));
        requirements.setMinimalOperatingSystemId(rs.getInt("minimal_operating_system"));
        requirements.setRecommendedOperatingSystemId(rs.getInt("recommended_operating_system"));
        return requirements;
    }
}
