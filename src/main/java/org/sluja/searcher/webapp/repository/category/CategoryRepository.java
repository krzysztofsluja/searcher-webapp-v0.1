package org.sluja.searcher.webapp.repository.category;

import org.sluja.searcher.webapp.model.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query("select c from Category c where c.name in ?1 and c.active = true")
    List<Category> findByNames(final Collection<String> names);

    @Query("""
            select c from Category c inner join c.contexts contexts
            where c.active = true and upper(contexts.name) like upper(?1) and contexts.active = true""")
    List<Category> findByContextName(final String name);
}