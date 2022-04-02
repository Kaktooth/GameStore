CREATE TABLE user_games
(
    id      SERIAL PRIMARY KEY,
    user_id UUID CONSTRAINT fk_user_id REFERENCES users (id) ON DELETE CASCADE,
    game_id UUID CONSTRAINT fk_game_id REFERENCES games (id) ON DELETE CASCADE
);