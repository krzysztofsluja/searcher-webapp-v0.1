-- Define a CTE to get all category IDs based on the provided category name 'seats'
WITH category_ids AS (
    SELECT id, name
    FROM category
    WHERE name = 'seats'
),
-- Insert new category properties if they do not already exist
     inserted_properties AS (
         INSERT INTO category_properties (value, active, created_date, updated_date)
    SELECT value, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
    FROM (VALUES
              ('siod')
         ) AS new_properties(value)
    WHERE NOT EXISTS (
        SELECT 1 FROM category_properties cp WHERE cp.value = new_properties.value
    )
    RETURNING id, value
     ),
-- Combine inserted properties with existing properties
     all_properties AS (
         SELECT id, value
         FROM inserted_properties
         UNION ALL
         SELECT id, value
         FROM category_properties
         WHERE value = 'siod'
     )
-- Insert associations between the category properties and categories, avoiding duplicates
INSERT INTO category_property_category (category_property_id, category_id)
SELECT ap.id, c.id
FROM all_properties ap
         JOIN category_ids c ON
    (c.name = 'seats' AND ap.value = 'siod')
WHERE NOT EXISTS (
    SELECT 1 FROM category_property_category cpc
    WHERE cpc.category_property_id = ap.id AND cpc.category_id = c.id
);
