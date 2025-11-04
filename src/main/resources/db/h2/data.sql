INSERT INTO base.roles
(id, code, name, description, created_at, updated_at)
VALUES
(RANDOM_UUID(), 'admin', 'Admin', 'Administrator role with full permissions', NOW(), NOW()),
(RANDOM_UUID(), 'user',  'User',  'Standard user for system',                NOW(), NOW());