CREATE TABLE game_genres
(
    id       UUID PRIMARY KEY,
    game_id  UUID CONSTRAINT fk_game_id REFERENCES games (id) ON DELETE CASCADE,
    genre_id INTEGER CONSTRAINT fk_genre_id REFERENCES genres (id)
);