CREATE TABLE transactions
(
    id            SERIAL PRIMARY KEY,
    date          TIMESTAMP      NOT NULL,
    amount        DECIMAL        NOT NULL,
    wallet_change DECIMAL        NOT NULL,
    wallet_amount DECIMAL        NOT NULL,
    user_id       UUID UNIQUE NOT NULL CONSTRAINT fk_user_id REFERENCES users (id) ON DELETE CASCADE,
    -- For now product is just a game
    product_id    INTEGER UNIQUE NOT NULL CONSTRAINT fk_product_id REFERENCES games (id) ON DELETE RESTRICT
);