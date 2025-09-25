-- -------------------------------
-- Systemdb Database
-- -------------------------------
CREATE DATABASE IF NOT EXISTS systemdb;
USE systemdb;

-- -------------------------------
-- Users Table
-- -------------------------------
CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    full_name VARCHAR(100) NOT NULL,
    cpf VARCHAR(20),
    email VARCHAR(100) NOT NULL UNIQUE,
    login VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    role ENUM('ADMINISTRATOR', 'MANAGER', 'COLLABORATOR') DEFAULT 'COLLABORATOR'
);

-- -------------------------------
-- Teams Table
-- -------------------------------
CREATE TABLE IF NOT EXISTS teams (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT
);

-- -------------------------------
-- Projects Table
-- -------------------------------
CREATE TABLE IF NOT EXISTS projects (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    start_date DATE,
    expected_end_date DATE,
    status ENUM('PLANNED','IN_PROGRESS','COMPLETED','CANCELED') DEFAULT 'PLANNED',
    manager_id INT,
    FOREIGN KEY (manager_id) REFERENCES users(id) ON DELETE SET NULL
);

-- -------------------------------
-- Team Members (Many-to-Many)
-- -------------------------------
CREATE TABLE IF NOT EXISTS team_members (
    team_id INT NOT NULL,
    user_id INT NOT NULL,
    PRIMARY KEY (team_id, user_id),
    FOREIGN KEY (team_id) REFERENCES teams(id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- -------------------------------
-- Project Teams (Many-to-Many)
-- -------------------------------
CREATE TABLE IF NOT EXISTS project_teams (
    project_id INT NOT NULL,
    team_id INT NOT NULL,
    PRIMARY KEY (project_id, team_id),
    FOREIGN KEY (project_id) REFERENCES projects(id) ON DELETE CASCADE,
    FOREIGN KEY (team_id) REFERENCES teams(id) ON DELETE CASCADE
);

-- -------------------------------
-- Insert Users
-- -------------------------------
INSERT INTO users (full_name, cpf, email, login, password, role)
VALUES 
('Administrator', '000.000.000-00', 'admin@example.com', 'admin', '1234', 'ADMINISTRATOR'),
('John Manager', '111.111.111-11', 'john.manager@example.com', 'jmanager', 'abcd', 'MANAGER'),
('Alice Collaborator', '222.222.222-22', 'alice.collab@example.com', 'acollab', 'pass', 'COLLABORATOR'),
('Bob Collaborator', '333.333.333-33', 'bob.collab@example.com', 'bcollab', 'pass', 'COLLABORATOR');

-- -------------------------------
-- Insert Teams
-- -------------------------------
INSERT INTO teams (name, description)
VALUES
('Alpha Team', 'Team working on Project Alpha'),
('Beta Team', 'Team working on Project Beta'),
('Gamma Team', 'Team working on Project Gamma');

-- -------------------------------
-- Insert Projects
-- -------------------------------
INSERT INTO projects (name, description, start_date, expected_end_date, status, manager_id)
VALUES
('Project A', 'Description Project A', '2025-09-01', '2025-12-31', 'IN_PROGRESS', 2),
('Project B', 'Description Project B', '2025-09-01', '2025-11-30', 'COMPLETED', 2),
('Project C', 'Description Project C', '2025-10-01', '2026-01-31', 'PLANNED', 2);

-- -------------------------------
-- Assign Users to Teams
-- -------------------------------
INSERT INTO team_members (team_id, user_id)
VALUES
(1, 2), -- John Manager in Alpha Team
(1, 3), -- Alice Collaborator in Alpha Team
(2, 3), -- Alice Collaborator in Beta Team
(2, 4), -- Bob Collaborator in Beta Team
(3, 4); -- Bob Collaborator in Gamma Team

-- -------------------------------
-- Assign Teams to Projects
-- -------------------------------
INSERT INTO project_teams (project_id, team_id)
VALUES
(1, 1), -- Alpha Team works on Project A
(1, 2), -- Beta Team works on Project A
(2, 2), -- Beta Team works on Project B
(3, 3); -- Gamma Team works on Project C
