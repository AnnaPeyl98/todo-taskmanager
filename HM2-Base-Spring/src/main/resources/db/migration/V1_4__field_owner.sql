ALTER TABLE task ADD COLUMN owner CHAR(36);
UPDATE task SET owner = (SELECT id FROM users WHERE username = 'admin') WHERE owner IS NULL;
ALTER TABLE task ALTER COLUMN owner SET NOT NULL;