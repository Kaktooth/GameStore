CREATE TABLE game_profiles
(
    id                SERIAL    PRIMARY KEY,
    price             DECIMAL   NOT NULL,
    title             VARCHAR   NOT NULL,
    developer         VARCHAR   NOT NULL,
    publisher         VARCHAR   NOT NULL,
    rating            INTEGER   NOT NULL,
    release_date      TIMESTAMP NOT NULL DEFAULT now(),
    description       VARCHAR   NOT NULL,
    brief_description VARCHAR   NOT NULL,
    game_id           UUID   UNIQUE CONSTRAINT fk_game_id REFERENCES games (id) ON DELETE CASCADE
);