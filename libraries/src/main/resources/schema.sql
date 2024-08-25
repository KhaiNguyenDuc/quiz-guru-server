DROP DATABASE IF EXISTS quizguru_library;
CREATE DATABASE quizguru_library;

USE quizguru_library;

DROP TABLE IF EXISTS words;
DROP TABLE IF EXISTS word_sets;
DROP TABLE IF EXISTS library;

CREATE TABLE libraries (
    id CHAR(36) PRIMARY KEY,
    user_id VARCHAR(36),
    created_at datetime NOT NULL,
    updated_at datetime NOT NULL,
    created_by VARCHAR(255) NULL,
    updated_by VARCHAR(255) NULL
);

CREATE TABLE word_sets (
    id CHAR(36) PRIMARY KEY,
    name VARCHAR(255),
    library_id CHAR(36),
    word_number INT,
    is_deleted BOOLEAN DEFAULT false,
    quiz_id VARCHAR(36),
    review_number INT,
    CONSTRAINT fk_library FOREIGN KEY (library_id) REFERENCES libraries(id),
    created_at datetime NOT NULL,
    updated_at datetime NOT NULL,
    created_by VARCHAR(255) NULL,
    updated_by VARCHAR(255) NULL
);

CREATE TABLE words (
    id CHAR(36) PRIMARY KEY,
    name VARCHAR(255),
    definition LONGTEXT,
    content LONGTEXT,
    word_set_id CHAR(36),
    CONSTRAINT fk_word_set FOREIGN KEY (word_set_id) REFERENCES word_sets(id),
    created_at datetime NOT NULL,
    updated_at datetime NOT NULL,
    created_by VARCHAR(255) NULL,
    updated_by VARCHAR(255) NULL
);
