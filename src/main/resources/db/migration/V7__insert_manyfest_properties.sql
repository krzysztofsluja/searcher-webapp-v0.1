-- Define CTE to get the context ID for the context with name 'skate'
WITH existing_context AS (
    SELECT id
    FROM context
    WHERE name = 'skate'
),

-- Define CTE to get the shop ID for the shop with name 'manyfestbmx'
     existing_shop AS (
         SELECT id
         FROM shop
         WHERE name = 'manyfestbmx'
     ),

-- Insert new shop attributes, avoiding duplicates
     inserted_shop_attributes AS (
         INSERT INTO shop_attribute (shop_id, context_id, name, value, insert_user_id, created_date, updated_date, active)
             SELECT es.id, ec.id, attr.name, attr.value, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true
             FROM (VALUES
                       ('shopName', 'manyfestbmx'),
                       ('productPrice','.product-description .product-price[content]'),
                       ('homePageAddress', 'https://www.manyfestbmx.pl'),
                       ('productDescription', 'div.rte-content'),
                       ('productDescriptionAttribute', ''),
                       ('productName', 'h2.h3.product-title'),
                       ('productDiscountPrice', 'span.red'),
                       ('productPageAddressAttribute', 'href'),
                       ('productImageExtractAttribute', 'data-src'),
                       ('div', 'div'),
                       ('pageAddressExtractAttribute', 'href'),
                       ('plainPageAddressToFormat', ''),
                       ('productInstance', 'div.js-product-miniature-wrapper article.product-miniature'),
                       ('productPageAddresses', 'h2.h3.product-title > a'),
                       ('productImageAddresses', 'a.thumbnail.product-thumbnail > img[data-full-size-image-url]'),
                       ('allCategoriesPageAddressElement', 'a'),
                       ('categoryPageAmounts', '.pagination a[rel=''next'']'),
                       ('allCategoriesPageAddresses', 'div.cbp-category-link-w > a'),
                       ('allCategoriesPageAddresses', 'li#cbp-hrmenu-tab-4.cbp-hrmenu-tab.cbp-hrmenu-tab-4 > a')
                  ) AS attr(name, value)
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
