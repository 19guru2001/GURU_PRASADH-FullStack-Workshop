-- Database Setup Script for Task Manager
-- Run this script to create the database and tables

-- Create database
CREATE DATABASE IF NOT EXISTS taskmanager;

-- Use the database
USE taskmanager;

-- Create tasks table
CREATE TABLE IF NOT EXISTS tasks (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    status VARCHAR(50) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    INDEX idx_status (status),
    INDEX idx_created_at (created_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Sample data (optional)
INSERT INTO tasks (title, description, status, created_at) VALUES
    ('Setup Development Environment', 'Install Java, Maven, MySQL, and IDE', 'COMPLETED', NOW()),
    ('Learn Spring Core', 'Study dependency injection and IoC container', 'IN_PROGRESS', NOW()),
    ('Build Task Manager', 'Create a task management application', 'PENDING', NOW());

-- Verify the setup
SELECT * FROM tasks;
