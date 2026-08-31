
-- Insert player mock data

INSERT INTO player (username, password, experience)
VALUES
    ('Alice123', '$2a$10$uutlt24y60bdJ9TApWxWN.ruMGWvNjg.ahu7Zyf/R8c5uBbiLjTa6', 0),  -- password123
    ('Bob_The_Builder', '$2a$10$KRLSlXJ219NuX0o48ZOWm.S.gSecszb3YOGrxqsokqBgtkh5CDzWi', 4), -- buildit99
    ('Charlie_RPG', '$2a$10$2oennL9WilNJwHNvnUgPh.4JzekDFtZ.quv1qw8tARaTzqUxkXo4.', 0)   -- charlie77
ON CONFLICT (username) DO NOTHING;

-- Insert hero mock data

INSERT INTO hero (name, strength_level, constitution_level, speed_level, player_id)
VALUES
('Jonny Dunce', 1, 1, 1, (SELECT id FROM player WHERE username = 'Alice123')),
('Sam Greenear', 2, 1, 2, (SELECT id FROM player WHERE username = 'Bob_The_Builder')),
('Perry the Veteran', 3, 3, 3, (SELECT id FROM player WHERE username = 'Bob_The_Builder'))
ON CONFLICT (name) DO NOTHING;

-- Insert monster mock data

INSERT INTO monster (name, strength_level, constitution_level, speed_level)
VALUES
('Goblin', 1, 1, 1),
('Thief', 1, 2, 3),
('Owlbear', 3, 4, 2)
ON CONFLICT (name) DO NOTHING;

-- Insert dungeon mock data

INSERT INTO dungeon (name, description, difficulty_level, monster_id)
VALUES
(
    'First Impressions',
    'After a long travel on a gravelly road, you hope to rest your feet at the “Drunken Bear” Tavern. Suddenly, you hear screaming in the distance.',
    1,
    (SELECT id FROM monster WHERE name = 'Goblin')
),
(
    'Sticky Fingers',
    'Deciding to take it easy after yesterday, you set out to explore the village. While inspecting the wares at a local shop, you suddenly feel a hand reaching for your coin purse.',
    2,
    (SELECT id FROM monster WHERE name = 'Thief')
),
(
    'The First Adventure',
    'This is it: your first real quest as an adventurer. You have people to impress and coin to earn, so don''t mess this up!',
    3,
    (SELECT id from monster WHERE name = 'Owlbear')
)
ON CONFLICT (name) DO NOTHING;

-- Insert hero_dungeon_completed mock data

INSERT INTO hero_dungeon_completed (hero_id, dungeon_id)
VALUES
(
    (SELECT id FROM hero WHERE name = 'Sam Greenear'), 
    (SELECT id FROM dungeon WHERE name = 'First Impressions')
),
(
    (SELECT id FROM hero WHERE name = 'Perry the Veteran'), 
    (SELECT id FROM dungeon WHERE name = 'First Impressions')
),
(
    (SELECT id FROM hero WHERE name = 'Perry the Veteran'), 
    (SELECT id FROM dungeon WHERE name = 'Sticky Fingers')
)
ON CONFLICT (hero_id, dungeon_id) DO NOTHING;

-- Insert player_dungeon_first_completions mock data

INSERT INTO player_dungeon_first_completions (player_id, dungeon_id)
VALUES
    (
        (SELECT id FROM player WHERE username = 'Bob_The_Builder'),
        (SELECT id FROM dungeon WHERE name = 'First Impressions')
    ),
    (
        (SELECT id FROM player WHERE username = 'Bob_The_Builder'),
        (SELECT id FROM dungeon WHERE name = 'Sticky Fingers')
    )
    ON CONFLICT (player_id, dungeon_id) DO NOTHING;