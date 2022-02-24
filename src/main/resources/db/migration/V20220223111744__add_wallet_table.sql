CREATE TABLE wallet
(
    id      SERIAL  PRIMARY KEY,
    amount  DECIMAL NOT NULL,
    user_id UUID    CONSTRAINT fk_user_id NOT NULL REFERENCES users (id) ON DELETE CASCADE
);