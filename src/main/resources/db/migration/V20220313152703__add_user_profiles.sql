CREATE TABLE user_profiles
(
    id              SERIAL PRIMARY KEY,
    public_username VARCHAR UNIQUE NOT NULL,
    public_image    bytea,
    user_id         UUID CONSTRAINT fk_user_id NOT NULL REFERENCES users (id) ON DELETE CASCADE
);