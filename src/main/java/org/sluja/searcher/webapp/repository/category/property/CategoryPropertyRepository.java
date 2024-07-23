package org.sluja.searcher.webapp.repository.category.property;

import org.sluja.searcher.webapp.model.category.property.CategoryProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryPropertyRepository extends JpaRepository<CategoryProperty, Long> {
    @Query(value = """
            SELECT cp.id AS cp_id, cp.value AS cp_value, cp.active AS cp_active,
                   c.id AS c_id, c.name AS c_name, c.active AS c_active 
                 FROM category_properties cp
                 INNER JOIN category_property_category cpc ON cp.id = cpc.category_property_id
                 INNER JOIN category c ON cpc.category_id = c.id
                 INNER JOIN category_contexts cctx ON c.id = cctx.category_id
                 INNER JOIN context ctx ON cctx.contexts_id = ctx.id
              WHERE
                 cp.active = true
                 AND c.name IN (:categories)
                 AND c.active = true
                 AND ctx.active = true
                 AND ctx.name LIKE :contextName
           """, nativeQuery = true)
    List<Object[]> findPropertiesForCategories(@Param("categories") final List<String> categories, @Param("contextName") final String contextName);

    @Query("""
            select c from CategoryProperty c
            where upper(c.category.name) = upper(?1) and c.active = true and c.category.active = true""")
    Optional<List<CategoryProperty>> findPropertiesForCategory(final String name);
}