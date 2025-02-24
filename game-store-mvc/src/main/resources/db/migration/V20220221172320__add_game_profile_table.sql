CREATE TABLE game_profiles
(
    id                  UUID PRIMARY KEY,
    release_date        TIMESTAMP NOT NULL DEFAULT now(),
    description         VARCHAR   NOT NULL,
    brief_description   VARCHAR   NOT NULL,
    game_id             UUID UNIQUE CONSTRAINT fk_game_id REFERENCES games (id) ON DELETE CASCADE
);