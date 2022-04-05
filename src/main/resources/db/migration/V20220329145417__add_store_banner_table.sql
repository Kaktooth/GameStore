CREATE TABLE store_banner
(
    user_id            UUID CONSTRAINT fk_user_id REFERENCES users (id) ON DELETE CASCADE,
    game_id            UUID CONSTRAINT fk_game_id REFERENCES games (id) ON DELETE CASCADE,
    image_id           INTEGER CONSTRAINT fk_image_id  REFERENCES images (image_id) ON DELETE CASCADE,
    banner_description VARCHAR NOT NULL,
    PRIMARY KEY (user_id, game_id)
);