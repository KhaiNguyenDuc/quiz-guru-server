DROP DATABASE IF EXISTS quizguru_record;
CREATE DATABASE quizguru_record;

USE quizguru_record;

DROP TABLE IF EXISTS record_item_choice;
DROP TABLE IF EXISTS record_item;
DROP TABLE IF EXISTS records;

CREATE TABLE records (
    id VARCHAR(36) NOT NULL PRIMARY KEY,
    score INT,
    duration INT,
    time_left INT,
    user_id VARCHAR(36),
    quiz_id VARCHAR(36),
    created_at datetime NOT NULL,
    updated_at datetime NOT NULL,
    created_by VARCHAR(255) NULL,
    updated_by VARCHAR(255) NULL
);

CREATE TABLE record_item (
    id VARCHAR(36) NOT NULL PRIMARY KEY,
    record_id VARCHAR(36),
    question_id VARCHAR(36),
    explanation NVARCHAR(200),
    FOREIGN KEY (record_id) REFERENCES records(id) ON DELETE CASCADE,
    created_at datetime NOT NULL,
    updated_at datetime NOT NULL,
    created_by VARCHAR(255) NULL,
    updated_by VARCHAR(255) NULL
);

CREATE TABLE record_item_choice (
    record_item_id VARCHAR(36),
    choice_id VARCHAR(36),
    PRIMARY KEY (record_item_id, choice_id),
    FOREIGN KEY (record_item_id) REFERENCES record_item(id) ON DELETE CASCADE
);