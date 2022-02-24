CREATE TABLE game_news_pictures
(
    id           SERIAL  PRIMARY KEY,
    picture      BYTEA   NOT NULL,
    game_news_id INTEGER UNIQUE CONSTRAINT fk_game_news_id NOT NULL REFERENCES game_news (id) ON DELETE CASCADE
);