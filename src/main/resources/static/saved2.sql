from bt_page_items('ix_gamep',1) limit 15;

select pg_size_pretty(pg_total_relation_size('ix_uploaded_games_brin'));
explain analyze  SELECT * FROM processors;

SELECT *
FROM uploaded_games
         INNER JOIN game_profiles gp on gp.game_id = uploaded_games.game_id
         INNER JOIN game_files gf ON uploaded_games.game_id = gf.game_id
         INNER JOIN users u ON uploaded_games.user_id = u.id
         INNER JOIN user_profiles up ON uploaded_games.user_id = up.user_id
         INNER JOIN system_requirements sr on gp.id = sr.game_profile_id
         INNER JOIN game_genres gg ON gf.game_id = gg.game_id
         INNER JOIN genres gn ON gn.id = gg.genre_id
WHERE to_tsvector('english', title || ' ' || gp.description) @@ to_tsquery('english', ?)
ORDER BY uploaded_games.game_id DESC;

CREATE INDEX pgweb_idx ON game_profiles USING GIN (to_tsvector('english', title));

SELECT to_tsvector('english', 'Discover a dark fantasy reimagining of the Wild West where lawmen and gunslingers share the frontier with fantastical creatures. Journey through the story of a group of atypical heroes, written into legend by the decisions you make in an unforgiving land. Each journey is unique and tailored to the actions taken - a series of high stakes adventures where everything counts and the world reacts to the choices you make. Form a posse or venture forth alone into an otherworldly confines of the Weird West and make each legend your own.

Weird West: Dark Fantasy reimagining of the Wild West where lawmen and gunslingers share the frontier with fantastical creatures, each playing with their own rules and their own peculiar motives.

Intertwined Destinies: Discover the world through origin stories of different characters, moving from one character''s journey to the next until all converge in a final chapter.

Bespoke Experience: Each playthrough is unique as the game tailors the story to the player''s actions and past choices for an ideal dramatic arc.

Immersive Sim: Weird West supports different styles of play in a simulated sandbox world where characters, factions, and even places react to a player''s decisions.');