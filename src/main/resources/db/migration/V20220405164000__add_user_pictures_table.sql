CREATE TABLE user_pictures
(
    id              SERIAL PRIMARY KEY,
    user_id         UUID UNIQUE CONSTRAINT fk_user_id REFERENCES users (id) ON DELETE CASCADE,
    image_id        INTEGER CONSTRAINT fk_image_id REFERENCES images (image_id)
);