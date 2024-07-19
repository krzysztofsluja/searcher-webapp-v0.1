WITH existing_record AS (
    SELECT id
    FROM context
    WHERE name = 'skate' AND insert_user_id = 1 AND active = true
)
INSERT INTO context (name, insert_user_id, created_date, updated_date, active)
SELECT 'skate', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true
WHERE NOT EXISTS (SELECT 1 FROM existing_record);