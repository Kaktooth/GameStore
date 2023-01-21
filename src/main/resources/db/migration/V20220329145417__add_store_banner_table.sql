CREATE TABLE store_banner
(
    id UUID PRIMARY KEY,
    user_id            UUID CONSTRAINT fk_user_id REFERENCES users (id) ON DELETE CASCADE,
    game_id            UUID CONSTRAINT fk_game_id REFERENCES games (id) ON DELETE CASCADE,
    image_id           UUID CONSTRAINT fk_image_id  REFERENCES images (id) ON DELETE CASCADE,
    description VARCHAR NOT NULL
);