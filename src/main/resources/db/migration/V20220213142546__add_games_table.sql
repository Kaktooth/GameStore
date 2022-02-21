CREATE TABLE games
(
    id        SERIAL PRIMARY KEY,
    name VARCHAR NOT NULL,
    object_id OID NOT NULL
);