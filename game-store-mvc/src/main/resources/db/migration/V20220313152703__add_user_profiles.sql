CREATE TABLE user_profiles
(
    id      UUID PRIMARY KEY,
    resume          VARCHAR,
    user_id         UUID CONSTRAINT fk_user_id NOT NULL REFERENCES users (id) ON DELETE CASCADE
);