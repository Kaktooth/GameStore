CREATE TABLE user_profiles
(
    profile_id      SERIAL PRIMARY KEY,
    public_username VARCHAR UNIQUE NOT NULL,
    resume          VARCHAR,
    image_id        INTEGER CONSTRAINT fk_image_id REFERENCES images (image_id),
    user_id         UUID CONSTRAINT fk_user_id NOT NULL REFERENCES users (id) ON DELETE CASCADE
);