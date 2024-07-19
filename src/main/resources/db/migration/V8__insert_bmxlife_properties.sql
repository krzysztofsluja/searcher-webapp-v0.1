-- Define CTE to get the context ID for the context with name 'skate'
WITH existing_context AS (
    SELECT id
    FROM context
    WHERE name = 'skate'
),

-- Define CTE to get the shop ID for the shop with name 'bmxlife'
     existing_shop AS (
         SELECT id
         FROM shop
         WHERE name = 'bmxlife'
     ),

-- Insert new shop attributes, avoiding duplicates
     inserted_shop_attributes AS (
         INSERT INTO shop_attribute (shop_id, context_id, name, value, insert_user_id, created_date, updated_date, active)
             SELECT es.id, ec.id, attr.name, attr.value, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true
             FROM (VALUES
                       ('shopName', 'bmxlife'),
                       ('productPrice','span.price'),
                       ('homePageAddress', 'https://bmxlife.pl'),
                       ('productDescription', 'div.tab-content div#pdesc p'),
                       ('productDescriptionAttribute', ''),
                       ('productName', 'h2.title'),
                       ('productDiscountPrice', 'span.red'),
                       ('productPageAddressAttribute', 'href'),
                       ('productImageExtractAttribute', 'src'),
                       ('div', 'div'),
                       ('pageAddressExtractAttribute', 'href'),
                       ('productInstance', 'div#products-container > div#products > div.col-prod > div.item'),
                       ('productPageAddresses', 'a'),
                       ('productImageAddresses', 'img'),
                       ('allCategoriesPageAddressElement', 'a'),
                       ('categoryPageAmounts', '.pagination a[rel=''next'']'),
                       ('allCategoriesPageAddresses', 'li.dropdown.megamenu-fw:has(a:containsOwn(cz)) > ul.dropdown-menu.megamenu-content li > a'))
                   AS attr(name, value)
                      CROSS JOIN existing_shop es
                      CROSS JOIN existing_context ec
             WHERE NOT EXISTS (
                 SELECT 1 FROM shop_attribute sa
                 WHERE sa.shop_id = es.id AND sa.context_id = ec.id AND sa.name = attr.name AND sa.value = attr.value
             )
             RETURNING id, shop_id, context_id, name, value
     )
-- This query part ensures that the script will return the inserted attributes
SELECT *
FROM inserted_shop_attributes;
