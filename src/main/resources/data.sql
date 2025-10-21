-- Create users table if it doesn't exist
CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    email VARCHAR(100) NOT NULL,
    balance DECIMAL(10,2) NOT NULL DEFAULT 0.00,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    is_active BOOLEAN DEFAULT TRUE
);

-- Insert test data
INSERT IGNORE INTO users (username, email, balance, is_active) VALUES 
('john_doe', 'john@example.com', 100.50, TRUE),
('jane_smith', 'jane@example.com', 250.75, TRUE),
('mike_wilson', 'mike@example.com', 75.25, TRUE),
('sarah_johnson', 'sarah@example.com', 500.00, TRUE),
('alex_brown', 'alex@example.com', 1000.00, TRUE);

-- Create teams table
CREATE TABLE IF NOT EXISTS teams (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    city VARCHAR(50),
    sport VARCHAR(50) NOT NULL
);

-- Insert test teams
INSERT IGNORE INTO teams (name, city, sport) VALUES 
('Lakers', 'Los Angeles', 'Basketball'),
('Warriors', 'Golden State', 'Basketball'),
('Cowboys', 'Dallas', 'Football'),
('Patriots', 'New England', 'Football');

-- Create games table
CREATE TABLE IF NOT EXISTS games (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    home_team_id BIGINT,
    away_team_id BIGINT,
    game_date TIMESTAMP,
    status VARCHAR(20) DEFAULT 'SCHEDULED',
    home_score INT DEFAULT 0,
    away_score INT DEFAULT 0,
    FOREIGN KEY (home_team_id) REFERENCES teams(id),
    FOREIGN KEY (away_team_id) REFERENCES teams(id)
);

-- Insert test games
INSERT IGNORE INTO games (home_team_id, away_team_id, game_date, status) VALUES 
(1, 2, '2025-10-25 19:00:00', 'SCHEDULED'),
(3, 4, '2025-10-26 13:00:00', 'SCHEDULED');