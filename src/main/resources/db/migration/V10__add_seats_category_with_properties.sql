-- Find the context with the name 'skate'
WITH existing_context AS (
    SELECT id
    FROM context
    WHERE name = 'skate'
),
-- Insert new categories if they do not already exist
     insert_categories AS (
         INSERT INTO category (name, active, insert_user_id, created_date, updated_date)
             SELECT name, true, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
             FROM (VALUES
                       ('seats')
                  ) AS new_categories(name)
             WHERE NOT EXISTS (
                 SELECT 1 FROM category c WHERE c.name = new_categories.name
             )
             RETURNING id, name
     ),
-- Select IDs of newly inserted and existing categories
     existing_and_new_categories AS (
         SELECT id, name FROM category
         WHERE name IN (
                        'seats'
             )
     )
-- Insert into the join table for the many-to-many relationship
INSERT INTO category_contexts (category_id, contexts_id)
SELECT eanc.id, ec.id
FROM existing_and_new_categories eanc, existing_context ec
WHERE NOT EXISTS (
    SELECT 1 FROM category_contexts cc
    WHERE cc.category_id = eanc.id AND cc.contexts_id = ec.id
);