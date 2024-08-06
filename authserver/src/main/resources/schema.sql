DROP DATABASE IF EXISTS quizguru_user;
CREATE DATABASE quizguru_user;

USE quizguru_user;

DROP TABLE IF EXISTS user_role;
DROP TABLE IF EXISTS refresh_token;
DROP TABLE IF EXISTS roles;
DROP TABLE IF EXISTS users;

CREATE TABLE users (
   id CHAR(36) NOT NULL PRIMARY KEY,
   email VARCHAR(255) NOT NULL,
   username VARCHAR(255) NOT NULL,
   password VARCHAR(255) NOT NULL UNIQUE,
   is_enabled INT NOT NULL
);

CREATE TABLE roles (
   id CHAR(36) NOT NULL PRIMARY KEY,
   name ENUM('USER', 'ADMIN') NOT NULL UNIQUE
);

CREATE TABLE user_role (
    user_id CHAR(36) NOT NULL,
    role_id CHAR(36) NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (role_id) REFERENCES roles(id)
);

CREATE TABLE refresh_token (
   id VARCHAR(255) PRIMARY KEY,
   token VARCHAR(255) NOT NULL,
   expired_date DATE NOT NULL,
   user_id CHAR(36) NOT NULL,
   FOREIGN KEY (user_id) REFERENCES users(id)
);
