package org.sluja.searcher.webapp.repository.category;

import org.sluja.searcher.webapp.model.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}