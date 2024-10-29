INSERT INTO records (id, score, duration, time_left, user_id, quiz_id, created_at, updated_at, created_by, updated_by) VALUES
    ('550e8400-e29b-41d4-a716-446655440050', 85, 30, 5, 'a8bc4bae-8bb6-464d-95f6-adb15d203720', '550e8400-e29b-41d4-a716-446655440000', NOW(), NOW(), 'a8bc4bae-8bb6-464d-95f6-adb15d203720', 'a8bc4bae-8bb6-464d-95f6-adb15d203720'),
    ('550e8400-e29b-41d4-a716-446655440051', 90, 45, 10, 'a8bc4bae-8bb6-464d-95f6-adb15d203720', '550e8400-e29b-41d4-a716-446655440001', NOW(), NOW(), 'a8bc4bae-8bb6-464d-95f6-adb15d203720', 'a8bc4bae-8bb6-464d-95f6-adb15d203720');

INSERT INTO record_item (id, record_id, question_id, explanation, created_at, updated_at, created_by, updated_by) VALUES
    ('550e8400-e29b-41d4-a716-446655440060', '550e8400-e29b-41d4-a716-446655440050', '550e8400-e29b-41d4-a716-446655440010', 'This is the explanation for the question about "ubiquitous".', NOW(), NOW(), 'a8bc4bae-8bb6-464d-95f6-adb15d203720', 'a8bc4bae-8bb6-464d-95f6-adb15d203720'),
    ('550e8400-e29b-41d4-a716-446655440061', '550e8400-e29b-41d4-a716-446655440051', '550e8400-e29b-41d4-a716-446655440011', 'This is the explanation for the synonyms of "happy".', NOW(), NOW(), 'a8bc4bae-8bb6-464d-95f6-adb15d203720', 'a8bc4bae-8bb6-464d-95f6-adb15d203720');

INSERT INTO record_item_choice (record_item_id, choice_id) VALUES
    ('550e8400-e29b-41d4-a716-446655440060', '550e8400-e29b-41d4-a716-446655440022'),
    ('550e8400-e29b-41d4-a716-446655440061', '550e8400-e29b-41d4-a716-446655440030'),
    ('550e8400-e29b-41d4-a716-446655440061', '550e8400-e29b-41d4-a716-446655440032'),
    ('550e8400-e29b-41d4-a716-446655440061', '550e8400-e29b-41d4-a716-446655440034');
