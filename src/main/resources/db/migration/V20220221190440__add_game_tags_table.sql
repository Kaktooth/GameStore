CREATE TABLE game_tags
(
    game_id INTEGER CONSTRAINT fk_game_id REFERENCES games (id) ON DELETE CASCADE,
    tag_id  INTEGER CONSTRAINT fk_tag_id REFERENCES tags (id),
    PRIMARY KEY (game_id, tag_id)
);