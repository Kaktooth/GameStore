CREATE TABLE uploaded_games
(
    user_id UUID CONSTRAINT fk_user_id REFERENCES users (id) ON DELETE CASCADE,
    game_id INTEGER CONSTRAINT fk_game_id REFERENCES games (id) ON DELETE CASCADE,
    PRIMARY KEY (user_id, game_id)
);