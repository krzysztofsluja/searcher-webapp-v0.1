WITH existing_context AS (
    SELECT id
    FROM context
    WHERE name = 'skate' AND insert_user_id = 1 AND active = true
),
     existing_shop AS (
         SELECT id
         FROM shop
         WHERE name = 'bmxlife' AND country = 'PL' AND context_id = (SELECT id FROM existing_context)
     ),
    inserted_shop AS (
INSERT INTO shop (name, country, context_id, insert_user_id, created_date, updated_date, active)
SELECT 'bmxlife', 'PL', id, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true
FROM existing_context
WHERE NOT EXISTS (SELECT 1 FROM existing_shop)
AND EXISTS (SELECT 1 FROM existing_context))

INSERT INTO shop_contexts (shop_id, context_id)
SELECT id, (SELECT id FROM existing_context)
FROM shop
WHERE name = 'bmxlife' AND country = 'PL' AND context_id = (SELECT id FROM existing_context)
AND NOT EXISTS (SELECT 1 FROM shop_contexts WHERE shop_id = id AND context_id = (SELECT id FROM existing_context));