CREATE TABLE purchase_history
(
    amount  DECIMAL   NOT NULL,
    purchase_date    TIMESTAMP NOT NULL,
    user_id UUID CONSTRAINT fk_user_id REFERENCES users (id) ON DELETE CASCADE,
    game_id UUID CONSTRAINT fk_game_id REFERENCES games (id) ON DELETE RESTRICT,
    PRIMARY KEY (user_id, game_id)
);