INSERT INTO libraries (id, user_id, created_at, updated_at, created_by, updated_by) VALUES
    ('a8bc4bae-8bb6-464d-95f6-adb15d203720', 'a8bc4bae-8bb6-464d-95f6-adb15d203720', NOW(), NOW(), 'a8bc4bae-8bb6-464d-95f6-adb15d203720', 'a8bc4bae-8bb6-464d-95f6-adb15d203720');

INSERT INTO word_sets (id, name, library_id, word_number, is_deleted, quiz_id, review_number, created_at, updated_at, created_by, updated_by) VALUES
    ('123e4567-e89b-12d3-a456-426614174100', 'English Vocabulary', 'a8bc4bae-8bb6-464d-95f6-adb15d203720', 20, false, '550e8400-e29b-41d4-a716-446655440000', 5, NOW(), NOW(), 'a8bc4bae-8bb6-464d-95f6-adb15d203720', 'a8bc4bae-8bb6-464d-95f6-adb15d203720');

INSERT INTO words (id, name, definition, content, word_set_id, created_at, updated_at, created_by, updated_by) VALUES
    ('123e4567-e89b-12d3-a456-426614174200', 'Alacrity', 'Brisk and cheerful readiness.', 'Alacrity is a strong eagerness to do something.', '123e4567-e89b-12d3-a456-426614174100', NOW(), NOW(), 'a8bc4bae-8bb6-464d-95f6-adb15d203720', 'a8bc4bae-8bb6-464d-95f6-adb15d203720'),
    ('123e4567-e89b-12d3-a456-426614174201', 'Cacophony', 'A harsh, discordant mixture of sounds.', 'The cacophony in the busy street was overwhelming.', '123e4567-e89b-12d3-a456-426614174100', NOW(), NOW(), 'a8bc4bae-8bb6-464d-95f6-adb15d203720', 'a8bc4bae-8bb6-464d-95f6-adb15d203720'),
    ('123e4567-e89b-12d3-a456-426614174202', 'Bonjour', 'A French greeting meaning "Good day".', 'Bonjour is used as a greeting during the day.', '123e4567-e89b-12d3-a456-426614174100', NOW(), NOW(), 'a8bc4bae-8bb6-464d-95f6-adb15d203720', 'a8bc4bae-8bb6-464d-95f6-adb15d203720'),
    ('123e4567-e89b-12d3-a456-426614174203', 'Merci', 'A French expression of gratitude.', 'Merci is the French word for thank you.', '123e4567-e89b-12d3-a456-426614174100', NOW(), NOW(), 'a8bc4bae-8bb6-464d-95f6-adb15d203720', 'a8bc4bae-8bb6-464d-95f6-adb15d203720');
