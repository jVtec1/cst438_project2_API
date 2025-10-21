-- Insert test users data
INSERT IGNORE INTO users (username, email, balance, is_active) VALUES 
('john_doe', 'john@example.com', 100.50, TRUE),
('jane_smith', 'jane@example.com', 250.75, TRUE),
('mike_wilson', 'mike@example.com', 75.25, TRUE),
('sarah_johnson', 'sarah@example.com', 500.00, TRUE),
('alex_brown', 'alex@example.com', 1000.00, TRUE);

-- Insert test teams
INSERT IGNORE INTO teams (name, city, conference, division) VALUES 
('Lakers', 'Los Angeles', 'Western', 'Pacific'),
('Warriors', 'Golden State', 'Western', 'Pacific'),
('Cowboys', 'Dallas', 'NFC', 'East'),
('Patriots', 'New England', 'AFC', 'East');

-- Insert test games
INSERT IGNORE INTO games (home_team_id, away_team_id, game_date, status) VALUES 
(1, 2, '2025-10-25 19:00:00', 'SCHEDULED'),
(3, 4, '2025-10-26 13:00:00', 'SCHEDULED');