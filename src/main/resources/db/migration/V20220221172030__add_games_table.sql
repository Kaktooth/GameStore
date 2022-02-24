CREATE TABLE games
(
    id        SERIAL PRIMARY KEY,
    file_name VARCHAR NOT NULL,
    object_id BIGINT  NOT NULL
);