CREATE TABLE users
(
    id           UUID PRIMARY KEY,
    username     VARCHAR UNIQUE NOT NULL,
    password     VARCHAR        NOT NULL,
    enabled      BOOLEAN        NOT NULL,
    phone_number VARCHAR        NOT NULL,
    email        VARCHAR,
    game_id      INTEGER        CONSTRAINT fk_game_id REFERENCES games (id)
);
