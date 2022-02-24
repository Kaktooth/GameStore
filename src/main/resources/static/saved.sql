SELECT lo_create(0);

SELECT lo_import('E:/net5.0.zip');
SELECT lo_import('E:/text.txt');
SELECT lo_import('C:/Program Files (x86)/s/net5.0.zip');
SELECT lo_open(38760, x'60000'::int);
SELECT lo_open(38761, x'60000'::int);
SELECT lo_export(38761, 'E:/text1.txt');
SELECT * FROM pg_largeobject;

INSERT INTO operating_systems VALUES (0, 'Windows 10'), (1, 'Windows 8'), (2, 'Windows 7');
INSERT INTO processors VALUES (0, 'Intel Xeon E-2386G'), (1, 'Intel Core i9-12900H'), (2, 'AMD Ryzen 7 5800X3D');
INSERT INTO graphics_cards VALUES (0, 'AMD Radeon RX 6600'), (1, 'AMD Radeon RX 6700 XT'), (2, 'MSI Geforce 210');

INSERT INTO tags VALUES (0, 'Indie'), (1, 'Funny'), (2, 'Multiplayer'), (3, 'Single player'), (4, 'Simulator');
INSERT INTO genres VALUES (0, 'Horror'), (1, 'Adventure'), (2, 'Shooter');

--
INSERT INTO games VALUES (0, 38761);
INSERT INTO game_profiles VALUES (0, 132, 'Gold Adventure', 'Might Developer', 'Clever Publisher', 0, '2020.12.2',
                                  'This is description', 'brief description', 0);

INSERT INTO minimal_system_requirements VALUES (0, 4, 12, 0, 0, 2, 2);
INSERT INTO required_system_requirements VALUES (0, 8, null, 0, 0, 2, 2);

INSERT INTO game_trailers VALUES (0, '\336\255\276\357');
INSERT INTO game_pictures VALUES (0, '\366\155\273\357', 0), (1, '\111\115\213\257', 0);

INSERT INTO game_tags VALUES (0, 1), (0, 3);
INSERT INTO game_genres VALUES (0, 1);

INSERT INTO favorite_games VALUES (0, 0);

INSERT INTO game_news VALUES (0, 'Big Update 2.0', 'In new update ...', '2021.2.3', 1422, 0);
INSERT INTO game_news_pictures VALUES (0, '\366\155\273\357', 0);

--
INSERT INTO games VALUES (1, 38760);
INSERT INTO game_profiles VALUES (1, 132, 'Horror with friends', 'Clever Developer', 'Clever Publisher', 0, '2020.12.2',
                                  'This is description', 'brief description', 1);

INSERT INTO minimal_system_requirements VALUES (1, 4, 12, 1, 0, 2, 2);
INSERT INTO required_system_requirements VALUES (1, 8, null, 1, 0, 2, 2);

INSERT INTO game_trailers VALUES (1, '\336\255\276\357');
INSERT INTO game_pictures VALUES (2, '\366\155\273\357', 1), (3, '\111\115\213\257', 1);

INSERT INTO game_tags VALUES (1, 2);
INSERT INTO game_genres VALUES (1, 0);

--
INSERT INTO users VALUES (gen_random_uuid(), 'User1', 'EncryptedPassword', true, 'skytrim@gmail.com');
INSERT INTO wallet VALUES (0, 0, '69ed268e-d0b7-40e5-aaa0-3bd86c7e1dcc');
INSERT INTO user_games VALUES ('69ed268e-d0b7-40e5-aaa0-3bd86c7e1dcc', 0);
INSERT INTO user_games VALUES ('69ed268e-d0b7-40e5-aaa0-3bd86c7e1dcc', 1);

--
DELETE FROM game_profiles WHERE id = 0;
DELETE FROM games WHERE id = 0;
DELETE FROM users WHERE id = '39269d8e-f116-4b5b-8f44-e6495ff97fac';