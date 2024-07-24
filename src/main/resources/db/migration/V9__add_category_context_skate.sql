WITH existing_context AS (
    SELECT id
    FROM context
    WHERE name = 'skate'
)

INSERT INTO category_contexts (category_id, contexts_id)
SELECT c.id, ec.id
FROM category c
         CROSS JOIN existing_context ec
WHERE NOT EXISTS (
    SELECT 1 FROM category_contexts cc
    WHERE cc.category_id = c.id AND cc.contexts_id = ec.id
);
