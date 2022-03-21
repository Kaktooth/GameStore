CREATE TABLE favorite_games
(
    id      SERIAL PRIMARY KEY,
    game_id UUID CONSTRAINT fk_game_id REFERENCES games (id) ON DELETE CASCADE
);