INSERT INTO library (id, user_id) VALUES
    ('123e4567-e89b-12d3-a456-426614174000', 'user123'),
    ('123e4567-e89b-12d3-a456-426614174001', 'user456');

INSERT INTO word_sets (id, name, library_id, word_number, is_deleted, quiz_id, review_number) VALUES
    ('123e4567-e89b-12d3-a456-426614174100', 'English Vocabulary', '123e4567-e89b-12d3-a456-426614174000', 20, false, 'quiz123', 5),
    ('123e4567-e89b-12d3-a456-426614174101', 'French Vocabulary', '123e4567-e89b-12d3-a456-426614174001', 15, false, 'quiz456', 3);

INSERT INTO words (id, name, definition, content, word_set_id) VALUES
    ('123e4567-e89b-12d3-a456-426614174200', 'Alacrity', 'Brisk and cheerful readiness.', 'Alacrity is a strong eagerness to do something.', '123e4567-e89b-12d3-a456-426614174100'),
    ('123e4567-e89b-12d3-a456-426614174201', 'Cacophony', 'A harsh, discordant mixture of sounds.', 'The cacophony in the busy street was overwhelming.', '123e4567-e89b-12d3-a456-426614174100'),
    ('123e4567-e89b-12d3-a456-426614174202', 'Bonjour', 'A French greeting meaning "Good day".', 'Bonjour is used as a greeting during the day.', '123e4567-e89b-12d3-a456-426614174101'),
    ('123e4567-e89b-12d3-a456-426614174203', 'Merci', 'A French expression of gratitude.', 'Merci is the French word for thank you.', '123e4567-e89b-12d3-a456-426614174101');
