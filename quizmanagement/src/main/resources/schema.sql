USE quizguru_quiz;

-- Drop children before parents to avoid FK constraint errors on re-run
DROP TABLE IF EXISTS record_item_choice;
DROP TABLE IF EXISTS record_item;
DROP TABLE IF EXISTS records;
DROP TABLE IF EXISTS choices;
DROP TABLE IF EXISTS questions;
DROP TABLE IF EXISTS words;
DROP TABLE IF EXISTS word_sets;
DROP TABLE IF EXISTS libraries;
DROP TABLE IF EXISTS quizzes;


CREATE TABLE quizzes (
 id CHAR(36) NOT NULL PRIMARY KEY,
 given_text LONGTEXT,
 type VARCHAR(50),
 number INT,
 language VARCHAR(255),
 level VARCHAR(50),
 duration INT,
 is_deleted BOOLEAN DEFAULT FALSE,
 user_id CHAR(36),
 created_at datetime NOT NULL,
 updated_at datetime NOT NULL,
 created_by VARCHAR(255) NULL,
 updated_by VARCHAR(255) NULL
);

CREATE TABLE questions (
   id CHAR(36) NOT NULL PRIMARY KEY,
   query TEXT,
   explanation LONGTEXT,
   type VARCHAR(50),
   quiz_id CHAR(36) NOT NULL,
   CONSTRAINT fk_question_quiz FOREIGN KEY (quiz_id) REFERENCES quizzes(id) ON DELETE CASCADE,
   created_at datetime NOT NULL,
   updated_at datetime NOT NULL,
   created_by VARCHAR(255) NULL,
   updated_by VARCHAR(255) NULL
);

CREATE TABLE choices (
 id CHAR(36) NOT NULL PRIMARY KEY,
 name VARCHAR(255),
 is_correct BOOLEAN DEFAULT FALSE,
 question_id CHAR(36) NOT NULL,
 CONSTRAINT fk_choice_question FOREIGN KEY (question_id) REFERENCES questions(id) ON DELETE CASCADE,
 created_at datetime NOT NULL,
 updated_at datetime NOT NULL,
 created_by VARCHAR(255) NULL,
 updated_by VARCHAR(255) NULL
);

CREATE TABLE libraries (
   id CHAR(36) PRIMARY KEY,
   user_id CHAR(36),
   created_at datetime NOT NULL,
   updated_at datetime NOT NULL,
   created_by VARCHAR(255) NULL,
   updated_by VARCHAR(255) NULL
);

CREATE TABLE word_sets (
   id CHAR(36) PRIMARY KEY,
   name VARCHAR(255),
   library_id CHAR(36) NOT NULL,
   CONSTRAINT fk_wordset_library FOREIGN KEY (library_id) REFERENCES libraries(id),
   word_number INT,
   is_deleted BOOLEAN DEFAULT false,
   quiz_id CHAR(36),
   CONSTRAINT fk_wordset_quiz FOREIGN KEY (quiz_id) REFERENCES quizzes(id),
   review_number INT,
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
word_set_id CHAR(36) NOT NULL,
CONSTRAINT fk_word_wordset FOREIGN KEY (word_set_id) REFERENCES word_sets(id),
created_at datetime NOT NULL,
updated_at datetime NOT NULL,
created_by VARCHAR(255) NULL,
updated_by VARCHAR(255) NULL
);

CREATE TABLE records (
 id CHAR(36) NOT NULL PRIMARY KEY,
 score INT,
 duration INT,
 time_left INT,
 user_id CHAR(36),
 quiz_id CHAR(36) NOT NULL,
 CONSTRAINT fk_record_quiz FOREIGN KEY (quiz_id) REFERENCES quizzes(id),
 created_at datetime NOT NULL,
 updated_at datetime NOT NULL,
 created_by VARCHAR(255) NULL,
 updated_by VARCHAR(255) NULL
);

CREATE TABLE record_item (
     id CHAR(36) NOT NULL PRIMARY KEY,
     record_id CHAR(36) NOT NULL,
     CONSTRAINT fk_recorditem_record FOREIGN KEY (record_id) REFERENCES records(id) ON DELETE CASCADE,
     question_id CHAR(36),
     CONSTRAINT fk_recorditem_question FOREIGN KEY (question_id) REFERENCES questions(id) ON DELETE SET NULL,
     explanation TEXT,
     created_at datetime NOT NULL,
     updated_at datetime NOT NULL,
     created_by VARCHAR(255) NULL,
     updated_by VARCHAR(255) NULL
);

CREATE TABLE record_item_choice (
            record_item_id CHAR(36),
            choice_id CHAR(36),
            PRIMARY KEY (record_item_id, choice_id),
            CONSTRAINT fk_ric_recorditem FOREIGN KEY (record_item_id) REFERENCES record_item(id) ON DELETE CASCADE,
            CONSTRAINT fk_ric_choice FOREIGN KEY (choice_id) REFERENCES choices(id) ON DELETE CASCADE
);