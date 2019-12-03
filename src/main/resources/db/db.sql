/**
 * Create user
 */
CREATE USER 'dbuser'@'localhost' IDENTIFIED BY 'dbuser';
GRANT ALL PRIVILEGES ON * . * TO 'dbuser'@'localhost';

CREATE USER 'dbuser'@'*' IDENTIFIED BY 'dbuser';
GRANT ALL PRIVILEGES ON * . * TO 'dbuser'@'*';

FLUSH PRIVILEGES;


/*
 * Create DATABASE
 */
CREATE DATABASE `sample`;