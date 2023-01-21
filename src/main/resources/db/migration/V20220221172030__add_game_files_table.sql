CREATE TABLE game_files
(
    id      UUID PRIMARY KEY,
    name    VARCHAR NOT NULL,
    file    BYTEA   NOT NULL,
    version VARCHAR NOT NULL,
    game_id UUID CONSTRAINT fk_game_id REFERENCES games (id) ON DELETE CASCADE
);