INSERT INTO roles (id, name, created_at, updated_at, created_by, updated_by) VALUES
    ('5d046a8b-350d-4c9b-bfae-beb4b83c1398', 'USER', NOW(), NOW(), null, null),
    ('992b95ad-b08c-4814-b4ec-54eaa673db56', 'ADMIN', NOW(), NOW(), null, null);

INSERT INTO users (id, email, username, password, library_id, is_enabled, created_at, updated_at, created_by, updated_by)
VALUES ('123e4567-e89b-12d3-a456-426614174000', 'duckhai1008@gmail.com', 'duckhai', '$2a$10$GYbJITHesRKTuLup/VyHDuHP6y/W8Q6j8RsbArAOzpj6BYcsqkley', '123e4567-e89b-12d3-a456-426614174000', 1, NOW(), NOW(), null , null);

INSERT INTO user_role (user_id, role_id) VALUES
    ('123e4567-e89b-12d3-a456-426614174000', '5d046a8b-350d-4c9b-bfae-beb4b83c1398'),
    ('123e4567-e89b-12d3-a456-426614174000', '992b95ad-b08c-4814-b4ec-54eaa673db56');