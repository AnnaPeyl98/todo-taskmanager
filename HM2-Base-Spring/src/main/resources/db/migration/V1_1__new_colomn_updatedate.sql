ALTER TABLE task ADD COLUMN updatedAt VARCHAR NULL;
UPDATE task SET updatedAt = createdAt WHERE updatedAt IS NULL;
ALTER TABLE task ALTER COLUMN updatedAt SET NOT NULL;