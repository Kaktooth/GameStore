CREATE TABLE game_trailers
(
    id    SERIAL PRIMARY KEY REFERENCES game_profiles (id) ON DELETE CASCADE,
    video BYTEA NOT NULL
);