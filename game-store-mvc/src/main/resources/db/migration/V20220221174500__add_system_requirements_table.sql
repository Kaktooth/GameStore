CREATE TABLE system_requirements
(
    id                              UUID PRIMARY KEY,
    minimal_memory                  INTEGER,
    recommended_memory              INTEGER,
    minimal_storage                 INTEGER,
    recommended_storage             INTEGER,
    game_profile_id                 UUID CONSTRAINT fk_game_profile_id REFERENCES game_profiles (id) ON DELETE CASCADE,
    minimal_processor_id            INTEGER CONSTRAINT fk_min_processor_id REFERENCES processors (id) ON DELETE RESTRICT,
    recommended_processor_id        INTEGER CONSTRAINT fk_rec_processor_id REFERENCES processors (id) ON DELETE RESTRICT,
    minimal_graphic_card_id         INTEGER CONSTRAINT fk_min_graphics_card_id REFERENCES graphics_cards (id) ON DELETE RESTRICT,
    recommended_graphic_card_id     INTEGER CONSTRAINT fk_rec_graphics_card_id REFERENCES graphics_cards (id) ON DELETE RESTRICT,
    minimal_operating_system_id     INTEGER CONSTRAINT fk_min_operating_system_id REFERENCES operating_systems (id) ON DELETE RESTRICT,
    recommended_operating_system_id INTEGER CONSTRAINT fk_rec_operating_system_id REFERENCES operating_systems (id) ON DELETE RESTRICT
);