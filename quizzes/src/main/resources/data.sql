INSERT INTO quizzes (id, given_text, type, number, language, level, duration, is_deleted, user_id, created_at, updated_at, created_by, updated_by) VALUES
    ('550e8400-e29b-41d4-a716-446655440000', 'Vocabulary Quiz 1', 'SINGLE_CHOICE_QUESTION', 1, 'English', 'EASY', 30, FALSE, 'a8bc4bae-8bb6-464d-95f6-adb15d203720', NOW(), NOW(), 'a8bc4bae-8bb6-464d-95f6-adb15d203720', 'a8bc4bae-8bb6-464d-95f6-adb15d203720');

INSERT INTO questions (id, query, explanation, type, quiz_id, created_at, updated_at, created_by, updated_by) VALUES
    ('550e8400-e29b-41d4-a716-446655440011', 'Select the synonyms for "happy".', 'This question tests the understanding of synonyms for the word "happy".', 'MULTIPLE_CHOICE', '550e8400-e29b-41d4-a716-446655440000', NOW(), NOW(), 'a8bc4bae-8bb6-464d-95f6-adb15d203720', 'a8bc4bae-8bb6-464d-95f6-adb15d203720');

INSERT INTO choices (id, name, is_correct, question_id, created_at, updated_at, created_by, updated_by) VALUES
    ('550e8400-e29b-41d4-a716-446655440030', 'Joyful', TRUE, '550e8400-e29b-41d4-a716-446655440011', NOW(), NOW(), 'a8bc4bae-8bb6-464d-95f6-adb15d203720', 'a8bc4bae-8bb6-464d-95f6-adb15d203720'),
    ('550e8400-e29b-41d4-a716-446655440031', 'Sad', FALSE, '550e8400-e29b-41d4-a716-446655440011', NOW(), NOW(), 'a8bc4bae-8bb6-464d-95f6-adb15d203720', 'a8bc4bae-8bb6-464d-95f6-adb15d203720'),
    ('550e8400-e29b-41d4-a716-446655440032', 'Content', TRUE, '550e8400-e29b-41d4-a716-446655440011', NOW(), NOW(), 'a8bc4bae-8bb6-464d-95f6-adb15d203720', 'a8bc4bae-8bb6-464d-95f6-adb15d203720'),
    ('550e8400-e29b-41d4-a716-446655440033', 'Miserable', FALSE, '550e8400-e29b-41d4-a716-446655440011', NOW(), NOW(), 'a8bc4bae-8bb6-464d-95f6-adb15d203720', 'a8bc4bae-8bb6-464d-95f6-adb15d203720');