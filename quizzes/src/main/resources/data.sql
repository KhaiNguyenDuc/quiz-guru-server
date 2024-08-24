-- Insert data into quizzes table
INSERT INTO quizzes (id, given_text, type, number, language, level, duration, is_deleted, user_id) VALUES
    ('550e8400-e29b-41d4-a716-446655440000', 'Vocabulary Quiz 1', 'SINGLE_CHOICE_QUESTION', 1, 'English', 'EASY', 30, FALSE, 'user-id-1'),
    ('550e8400-e29b-41d4-a716-446655440001', 'Vocabulary Quiz 2', 'MULTIPLE_CHOICE_QUESTION', 2, 'Spanish', 'MEDIUM', 45, FALSE, 'user-id-2');

-- Insert data into questions table
INSERT INTO questions (id, query, explanation, type, quiz_id) VALUES
    ('550e8400-e29b-41d4-a716-446655440010', 'What is the meaning of "ubiquitous"?', 'This question tests the understanding of the word "ubiquitous".', 'SINGLE_CHOICE', '550e8400-e29b-41d4-a716-446655440000'),
    ('550e8400-e29b-41d4-a716-446655440011', 'Select the synonyms for "happy".', 'This question tests the understanding of synonyms for the word "happy".', 'MULTIPLE_CHOICE', '550e8400-e29b-41d4-a716-446655440001');

-- Insert data into choices table
INSERT INTO choices (id, name, question_id, is_correct) VALUES
    ('550e8400-e29b-41d4-a716-446655440020', 'Common', '550e8400-e29b-41d4-a716-446655440010', FALSE),
    ('550e8400-e29b-41d4-a716-446655440021', 'Rare', '550e8400-e29b-41d4-a716-446655440010', FALSE),
    ('550e8400-e29b-41d4-a716-446655440022', 'Present everywhere', '550e8400-e29b-41d4-a716-446655440010', TRUE),
    ('550e8400-e29b-41d4-a716-446655440023', 'Unusual', '550e8400-e29b-41d4-a716-446655440010', FALSE),

    ('550e8400-e29b-41d4-a716-446655440030', 'Joyful', '550e8400-e29b-41d4-a716-446655440011', TRUE),
    ('550e8400-e29b-41d4-a716-446655440031', 'Sad', '550e8400-e29b-41d4-a716-446655440011', FALSE),
    ('550e8400-e29b-41d4-a716-446655440032', 'Content', '550e8400-e29b-41d4-a716-446655440011', TRUE),
    ('550e8400-e29b-41d4-a716-446655440033', 'Miserable', '550e8400-e29b-41d4-a716-446655440011', FALSE),
    ('550e8400-e29b-41d4-a716-446655440034', 'Cheerful', '550e8400-e29b-41d4-a716-446655440011', TRUE);
