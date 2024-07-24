package org.sluja.searcher.webapp.mapper.category;

import org.sluja.searcher.webapp.model.category.Category;
import org.sluja.searcher.webapp.model.category.property.CategoryProperty;

public class CategoryPropertyEntityMapper {

    public static CategoryProperty map(final Object[] objects) {
        //TODO validation
        final CategoryProperty categoryProperty = new CategoryProperty();
        final Category category = new Category();

        categoryProperty.setId(((Number) objects[0]).longValue());
        categoryProperty.setValue((String) objects[1]);
        categoryProperty.setActive((Boolean) objects[2]);

        category.setId(((Number) objects[3]).longValue());
        category.setName((String) objects[4]);
        category.setActive((Boolean) objects[5]);

        categoryProperty.setCategory(category);
        return categoryProperty;
    }
}
