/**
 * Create user
 */
CREATE USER 'decuser'@'localhost' IDENTIFIED BY 'decuser';
GRANT ALL PRIVILEGES ON * . * TO 'decuser'@'localhost';

CREATE USER 'decuser'@'*' IDENTIFIED BY 'decuser';
GRANT ALL PRIVILEGES ON * . * TO 'decuser'@'*';

FLUSH PRIVILEGES;


/*
 * Create DATABASE
 */
CREATE DATABASE `dec`;