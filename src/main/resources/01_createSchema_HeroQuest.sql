-- Drop tables for development

    DROP TABLE if EXISTS hero_dungeon_completed;
    DROP TABLE if EXISTS hero;
    DROP TABLE if EXISTS dungeon;
    DROP TABLE if EXISTS monster;

-- Create hero table:

CREATE TABLE IF NOT EXISTS hero (
	id SERIAL PRIMARY KEY,
	name VARCHAR(20) UNIQUE NOT NULL CHECK (TRIM(name) <> ''), -- Checks that the trimmed name is not an empty String
	strength_level INT NOT NULL CHECK (strength_level > 0), -- Checks that the level value is not negative or 0
	constitution_level INT NOT NULL CHECK (constitution_level > 0),
	speed_level INT NOT NULL CHECK (speed_level > 0)
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