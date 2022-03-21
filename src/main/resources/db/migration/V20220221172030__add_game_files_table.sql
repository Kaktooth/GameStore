CREATE TABLE game_files
(
    id        SERIAL PRIMARY KEY,
    file_name VARCHAR NOT NULL,
    object_id BIGINT  NOT NULL,
    version   VARCHAR NOT NULL,
    game_id   UUID UNIQUE CONSTRAINT fk_game_id REFERENCES games (id) ON DELETE CASCADE
);