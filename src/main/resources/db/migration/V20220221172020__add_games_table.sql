CREATE TABLE games
(
    id        UUID PRIMARY KEY,
    title     VARCHAR NOT NULL,
    price     DECIMAL NOT NULL,
    developer VARCHAR NOT NULL,
    publisher VARCHAR
);