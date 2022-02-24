CREATE TABLE required_system_requirements
(
    id               SERIAL  PRIMARY KEY,
    ram              INTEGER, -- ram - Random Access Memory
    disk_free_memory INTEGER,
    profile_id       INTEGER CONSTRAINT fk_profile_id REFERENCES game_profiles (id) ON DELETE CASCADE,
    processor        INTEGER CONSTRAINT fk_min_processor_id REFERENCES processors (id) ON DELETE RESTRICT,
    graphics_card    INTEGER CONSTRAINT fk_min_graphics_card_id REFERENCES graphics_cards (id) ON DELETE RESTRICT,
    operating_system INTEGER CONSTRAINT fk_min_operating_system_id REFERENCES operating_systems (id) ON DELETE RESTRICT
);