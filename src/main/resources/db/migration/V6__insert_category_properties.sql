-- Define a CTE to get all category IDs based on the provided category names
WITH category_ids AS (
    SELECT id, name
    FROM category
    WHERE name IN (
                   'bars', 'frames', 'rims', 'hubs', 'spokes', 'gears',
                   'tires', 'pegs', 'hubguards', 'grips', 'stems', 'barends',
                   'heads', 'chains', 'supports', 'pedals', 'posts'
        )
),
-- Insert new category properties if they do not already exist
     inserted_properties AS (
         INSERT INTO category_properties (value, active, created_date, updated_date)
             SELECT value, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
             FROM (VALUES
                       ('kierownice'), ('kierownica'), ('ramy'), ('rama'), ('ram'),
                       ('obrecz'), ('obrecze'), ('felga'), ('felgi'), ('piasta'), ('piasty'),
                       ('szprycha'), ('szprychy'), ('zebatka'), ('zebatki'), ('opona'),
                       ('opony'), ('pegi'), ('peg'), ('hubguardy'), ('hubguard'), ('grip'),
                       ('gripy'), ('chwyty'), ('chwyt'), ('mostki'), ('mosty'), ('most'),
                       ('wsporniki'), ('wspornik'), ('barendy'), ('barend'), ('stery'),
                       ('ster'), ('lancuchy'), ('lancuch'), ('supporty'), ('support'),
                       ('suporty'), ('suport'), ('pedal'), ('pedaly'), ('sztyce'),
                       ('sztyca'), ('sztyc')
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
         WHERE value IN (
                         'kierownice', 'kierownica', 'ramy', 'rama', 'ram', 'obrecz',
                         'obrecze', 'felga', 'felgi', 'piasta', 'piasty', 'szprycha',
                         'szprychy', 'zebatka', 'zebatki', 'opona', 'opony', 'pegi', 'peg',
                         'hubguardy', 'hubguard', 'grip', 'gripy', 'chwyty', 'chwyt',
                         'mostki', 'mosty', 'most', 'wsporniki', 'wspornik', 'barendy',
                         'barend', 'stery', 'ster', 'lancuchy', 'lancuch', 'supporty',
                         'support', 'suporty', 'suport', 'pedal', 'pedaly', 'sztyce',
                         'sztyca', 'sztyc'
             )
     )
-- Insert associations between the category properties and categories, avoiding duplicates
INSERT INTO category_property_category (category_property_id, category_id)
SELECT ap.id, c.id
FROM all_properties ap
         JOIN category_ids c ON
    (c.name = 'bars' AND ap.value IN ('kierownice', 'kierownica')) OR
    (c.name = 'frames' AND ap.value IN ('ramy', 'rama', 'ram')) OR
    (c.name = 'rims' AND ap.value IN ('obrecz', 'obrecze', 'felga', 'felgi')) OR
    (c.name = 'hubs' AND ap.value IN ('piasta', 'piasty')) OR
    (c.name = 'spokes' AND ap.value IN ('szprycha', 'szprychy')) OR
    (c.name = 'gears' AND ap.value IN ('zebatka', 'zebatki')) OR
    (c.name = 'tires' AND ap.value IN ('opona', 'opony')) OR
    (c.name = 'pegs' AND ap.value IN ('pegi', 'peg')) OR
    (c.name = 'hubguards' AND ap.value IN ('hubguardy', 'hubguard')) OR
    (c.name = 'grips' AND ap.value IN ('grip', 'gripy', 'chwyty', 'chwyt')) OR
    (c.name = 'stems' AND ap.value IN ('mostki', 'mosty', 'most', 'wsporniki', 'wspornik')) OR
    (c.name = 'barends' AND ap.value IN ('barendy', 'barend')) OR
    (c.name = 'heads' AND ap.value IN ('stery', 'ster')) OR
    (c.name = 'chains' AND ap.value IN ('lancuchy', 'lancuch')) OR
    (c.name = 'supports' AND ap.value IN ('supporty', 'support', 'suporty', 'suport')) OR
    (c.name = 'pedals' AND ap.value IN ('pedal', 'pedaly')) OR
    (c.name = 'posts' AND ap.value IN ('sztyce', 'sztyca', 'sztyc'))
WHERE NOT EXISTS (
    SELECT 1 FROM category_property_category cpc
    WHERE cpc.category_property_id = ap.id AND cpc.category_id = c.id
);
