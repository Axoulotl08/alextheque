-- NETTOYAGE DES TABLES EXISTANTES

DELETE FROM game;
DELETE FROM console;

-- REINITIALISATION DES AUTO INCREMENTS
ALTER TABLE game AUTO_INCREMENT = 1;
ALTER TABLE console AUTO_INCREMENT = 1;

-- INSERTION DES consoleS
INSERT INTO console(console_id, console_name, console_launch_date, console_manufacturer, console_zone) VALUES
(1, 'PS5', '2020-11-19 00:00:00', 'Sony', 'EUR'),
(2, 'XBOITE', '2024-12-19 00:00:00', 'Microcsoft', 'JAP'),
(3, 'GC', '2018-09-11 00:00:00', 'Nintendo', 'USA');

-- INSERTION DES JEUX
INSERT INTO game(game_id, game_name, console_id, game_inbox, game_start_date, game_status) VALUES
(1, 'TLOU', 1, 1, '2020-06-19', 'TO_START'),
(2, 'BOTW', 3, 1, '2026-08-19', 'IN_PROGRESS'),
(3, 'Gears of War', 2, 1, '2023-05-29', 'DONE'),
(4, 'MK8', 3, 1, '2022-06-19', 'COMPLETE'),
(5, 'God Of War', 1, 1, '2024-07-19', 'COMPLETE'),
(6, 'Ragnarok God Of War', 1, 1, '2026-07-19', 'COMPLETE');