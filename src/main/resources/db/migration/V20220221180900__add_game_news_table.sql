CREATE TABLE game_news
(
    id               SERIAL PRIMARY KEY,
    label            VARCHAR   NOT NULL,
    description      VARCHAR   NOT NULL,
    publication_date TIMESTAMP DEFAULT now() NOT NULL,
    rating           INTEGER   NOT NULL,
    game_id          INTEGER CONSTRAINT fk_game_id NOT NULL REFERENCES games (id) ON DELETE CASCADE
);