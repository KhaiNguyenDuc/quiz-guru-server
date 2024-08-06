DROP DATABASE IF EXISTS quizguru_quiz;
CREATE DATABASE quizguru_quiz;

USE quizguru_quiz;

DROP TABLE IF EXISTS choices;
DROP TABLE IF EXISTS questions;
DROP TABLE IF EXISTS quizzes;

CREATE TABLE quizzes (
    id VARCHAR(36) NOT NULL PRIMARY KEY,
    given_text LONGTEXT,
    type VARCHAR(50),
    number INT,
    language VARCHAR(255),
    level VARCHAR(50),
    duration INT,
    is_deleted BOOLEAN DEFAULT FALSE,
    user_id VARCHAR(36)
);

CREATE TABLE questions (
    id VARCHAR(36) NOT NULL PRIMARY KEY,
    query TEXT,
    explanation LONGTEXT,
    type VARCHAR(50),
    quiz_id VARCHAR(36),
    CONSTRAINT fk_quiz
    FOREIGN KEY (quiz_id)
    REFERENCES quizzes(id)
    ON DELETE CASCADE
);

CREATE TABLE choices (
    id VARCHAR(36) NOT NULL PRIMARY KEY,
    name VARCHAR(255),
    is_correct BOOLEAN DEFAULT FALSE,
    question_id VARCHAR(36),
    CONSTRAINT fk_question
    FOREIGN KEY (question_id)
    REFERENCES questions(id)
    ON DELETE CASCADE
);