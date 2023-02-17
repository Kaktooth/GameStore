CREATE TABLE user_pictures
(
    id              UUID PRIMARY KEY,
    user_id         UUID UNIQUE CONSTRAINT fk_user_id REFERENCES users (id) ON DELETE CASCADE,
    image_id        UUID CONSTRAINT fk_image_id REFERENCES images (id)
);