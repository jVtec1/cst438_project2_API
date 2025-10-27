-- Insert test users data-- Insert test users data

INSERT IGNORE INTO users (username, password, email, balance, is_active) VALUES INSERT IGNORE INTO users (username, password, email, balance, is_active) VALUES 

('testUser1', '1234', 'test1@example.com', 100.50, TRUE),('testUser1', '1234', 'test1@example.com', 100.50, TRUE),

('testUser2', '1234', 'test2@example.com', 250.75, TRUE),('testUser2', '1234', 'test2@example.com', 250.75, TRUE),

('john_doe', 'password123', 'john@example.com', 500.00, TRUE);('john_doe', 'password123', 'john@example.com', 500.00, TRUE);



-- Insert NBA teams with both schemas (for compatibility)-- Insert NBA teams matching frontend data

INSERT IGNORE INTO teams (team_id, team_name, nickname, name, city, conference, division, logo_url) VALUES INSERT IGNORE INTO teams (team_id, team_name, nickname, logo_url, city, conference, division) VALUES 

(1, 'Atlanta Hawks', 'Hawks', 'Hawks', 'Atlanta', 'Eastern', 'Southeast', 'https://upload.wikimedia.org/wikipedia/en/2/24/Atlanta_Hawks_logo.svg'),(1, 'Atlanta Hawks', 'Hawks', 'https://upload.wikimedia.org/wikipedia/en/2/24/Atlanta_Hawks_logo.svg', 'Atlanta', 'Eastern', 'Southeast'),

(2, 'Boston Celtics', 'Celtics', 'Celtics', 'Boston', 'Eastern', 'Atlantic', 'https://upload.wikimedia.org/wikipedia/fr/6/65/Celtics_de_Boston_logo.svg'),(2, 'Boston Celtics', 'Celtics', 'https://upload.wikimedia.org/wikipedia/fr/6/65/Celtics_de_Boston_logo.svg', 'Boston', 'Eastern', 'Atlantic'),

(11, 'Golden State Warriors', 'Warriors', 'Warriors', 'San Francisco', 'Western', 'Pacific', 'https://upload.wikimedia.org/wikipedia/en/0/01/Golden_State_Warriors_logo.svg'),(11, 'Golden State Warriors', 'Warriors', 'https://upload.wikimedia.org/wikipedia/en/0/01/Golden_State_Warriors_logo.svg', 'San Francisco', 'Western', 'Pacific'),

(17, 'Los Angeles Lakers', 'Lakers', 'Lakers', 'Los Angeles', 'Western', 'Pacific', 'https://upload.wikimedia.org/wikipedia/commons/3/3c/Los_Angeles_Lakers_logo.svg'),(17, 'Los Angeles Lakers', 'Lakers', 'https://upload.wikimedia.org/wikipedia/commons/3/3c/Los_Angeles_Lakers_logo.svg', 'Los Angeles', 'Western', 'Pacific'),

(20, 'Miami Heat', 'Heat', 'Heat', 'Miami', 'Eastern', 'Southeast', 'https://upload.wikimedia.org/wikipedia/en/f/fb/Miami_Heat_logo.svg'),(20, 'Miami Heat', 'Heat', 'https://upload.wikimedia.org/wikipedia/en/f/fb/Miami_Heat_logo.svg', 'Miami', 'Eastern', 'Southeast'),

(24, 'New York Knicks', 'Knicks', 'Knicks', 'New York', 'Eastern', 'Atlantic', 'https://upload.wikimedia.org/wikipedia/en/2/25/New_York_Knicks_logo.svg');(24, 'New York Knicks', 'Knicks', 'https://upload.wikimedia.org/wikipedia/en/2/25/New_York_Knicks_logo.svg', 'New York', 'Eastern', 'Atlantic');

-- Insert test favorites
INSERT IGNORE INTO favorites (user_id, team_id) VALUES (1, 1);