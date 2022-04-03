CREATE TABLE game_pictures
(
    id              SERIAL PRIMARY KEY,
    game_id         UUID CONSTRAINT fk_game_id REFERENCES games (id) ON DELETE CASCADE,
    picture_type_id INTEGER CONSTRAINT fk_picture_type_id REFERENCES game_picture_types (type_id),
    image_id        INTEGER CONSTRAINT fk_image_id REFERENCES images (image_id)
);