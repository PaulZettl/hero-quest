-- Drop tables for development

    DROP TABLE if EXISTS player_dungeon_first_completions CASCADE;
    DROP TABLE if EXISTS hero_dungeon_completed CASCADE;
    DROP TABLE if EXISTS hero CASCADE;
	DROP TABLE IF EXISTS player CASCADE;
    DROP TABLE if EXISTS dungeon CASCADE;
    DROP TABLE if EXISTS monster CASCADE;

-- Create player table:

CREATE TABLE IF NOT EXISTS player (
    id SERIAL PRIMARY KEY,
    username VARCHAR (20) UNIQUE NOT NULL CHECK (TRIM(username) <> ''),
    password VARCHAR (255) NOT NULL,
    experience INT NOT NULL
);

-- Create hero table:

CREATE TABLE IF NOT EXISTS hero (
    id SERIAL PRIMARY KEY,
    name VARCHAR(20) UNIQUE NOT NULL CHECK (TRIM(name) <> ''),
    strength_level INT NOT NULL CHECK (strength_level > 0), 
    constitution_level INT NOT NULL CHECK (constitution_level > 0),
    speed_level INT NOT NULL CHECK (speed_level > 0),
    player_id INT NOT NULL,
    FOREIGN KEY (player_id) REFERENCES player(id)
);


-- Create monster table:

CREATE TABLE IF NOT EXISTS monster (
	id SERIAL PRIMARY KEY,
	name VARCHAR(20) UNIQUE NOT NULL CHECK (TRIM(name) <> ''),
	strength_level INT NOT NULL CHECK (strength_level > 0),
	constitution_level INT NOT NULL CHECK (constitution_level > 0),
	speed_level INT NOT NULL CHECK (speed_level > 0)
	);

-- Create dungeon table:

CREATE TABLE IF NOT EXISTS dungeon (
	id SERIAL PRIMARY KEY,
	name VARCHAR(50) UNIQUE NOT NULL CHECK (TRIM(name) <> ''),
	description TEXT NOT NULL,
	difficulty_level INT NOT NULL CHECK (difficulty_level > 0),
	monster_id INT UNIQUE NOT NULL,
	FOREIGN KEY (monster_id) REFERENCES monster(id)
	);

-- Create hero_dungeon_completed table:

CREATE TABLE IF NOT EXISTS hero_dungeon_completed (
	hero_id INT NOT NULL,
	dungeon_id INT NOT NULL,
	PRIMARY KEY (hero_id, dungeon_id),
	FOREIGN KEY (hero_id) REFERENCES hero(id) ON DELETE CASCADE,
	FOREIGN KEY (dungeon_id) REFERENCES dungeon(id) ON DELETE CASCADE
	);

-- Create player_dungeon_first_completion table

CREATE TABLE IF NOT EXISTS player_dungeon_first_completions (
    player_id INT NOT NULL,
    dungeon_id INT NOT NULL,
    PRIMARY KEY (player_id, dungeon_id),
    FOREIGN KEY (player_id) REFERENCES player(id) ON DELETE CASCADE,
    FOREIGN KEY (dungeon_id) REFERENCES dungeon(id) ON DELETE CASCADE
)