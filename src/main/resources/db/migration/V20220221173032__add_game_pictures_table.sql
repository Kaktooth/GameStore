CREATE TABLE game_pictures
(
    id      SERIAL PRIMARY KEY,
    picture BYTEA NOT NULL,
    game_id INTEGER CONSTRAINT fk_game_id REFERENCES games (id) ON DELETE CASCADE
);