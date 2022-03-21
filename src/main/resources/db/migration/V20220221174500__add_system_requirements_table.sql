CREATE TABLE system_requirements
(
    id                           SERIAL PRIMARY KEY,
    minimal_ram                  INTEGER,
    recommended_ram              INTEGER,
    minimal_disk_free_memory     INTEGER,
    recommended_disk_free_memory INTEGER,
    game_profile_id              INTEGER CONSTRAINT fk_game_profile_id REFERENCES game_profiles (id) ON DELETE CASCADE,
    minimal_processor            INTEGER CONSTRAINT fk_min_processor_id REFERENCES processors (id) ON DELETE RESTRICT,
    recommended_processor        INTEGER CONSTRAINT fk_rec_processor_id REFERENCES processors (id) ON DELETE RESTRICT,
    minimal_graphics_card        INTEGER CONSTRAINT fk_min_graphics_card_id REFERENCES graphics_cards (id) ON DELETE RESTRICT,
    recommended_graphics_card    INTEGER CONSTRAINT fk_rec_graphics_card_id REFERENCES graphics_cards (id) ON DELETE RESTRICT,
    minimal_operating_system     INTEGER CONSTRAINT fk_min_operating_system_id REFERENCES operating_systems (id) ON DELETE RESTRICT,
    recommended_operating_system INTEGER CONSTRAINT fk_rec_operating_system_id REFERENCES operating_systems (id) ON DELETE RESTRICT
);