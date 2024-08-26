INSERT INTO libraries (id, user_id, created_at, updated_at, created_by, updated_by) VALUES
    ('123e4567-e89b-12d3-a456-426614174000', '123e4567-e89b-12d3-a456-426614174000', NOW(), NOW(), '123e4567-e89b-12d3-a456-426614174000', '123e4567-e89b-12d3-a456-426614174000');

INSERT INTO word_sets (id, name, library_id, word_number, is_deleted, quiz_id, review_number, created_at, updated_at, created_by, updated_by) VALUES
    ('123e4567-e89b-12d3-a456-426614174100', 'English Vocabulary', '123e4567-e89b-12d3-a456-426614174000', 20, false, '550e8400-e29b-41d4-a716-446655440000', 5, NOW(), NOW(), '123e4567-e89b-12d3-a456-426614174000', '123e4567-e89b-12d3-a456-426614174000');

INSERT INTO words (id, name, definition, content, word_set_id, created_at, updated_at, created_by, updated_by) VALUES
    ('123e4567-e89b-12d3-a456-426614174200', 'Alacrity', '', '', '123e4567-e89b-12d3-a456-426614174100', NOW(), NOW(), '123e4567-e89b-12d3-a456-426614174000', '123e4567-e89b-12d3-a456-426614174000'),
    ('123e4567-e89b-12d3-a456-426614174201', 'Cacophony', '', '', '123e4567-e89b-12d3-a456-426614174100', NOW(), NOW(), '123e4567-e89b-12d3-a456-426614174000', '123e4567-e89b-12d3-a456-426614174000'),
    ('123e4567-e89b-12d3-a456-426614174202', 'Bonjour', '', '', '123e4567-e89b-12d3-a456-426614174100', NOW(), NOW(), '123e4567-e89b-12d3-a456-426614174000', '123e4567-e89b-12d3-a456-426614174000'),
    ('123e4567-e89b-12d3-a456-426614174203', 'Merci', '', '', '123e4567-e89b-12d3-a456-426614174100', NOW(), NOW(), '123e4567-e89b-12d3-a456-426614174000', '123e4567-e89b-12d3-a456-426614174000');
