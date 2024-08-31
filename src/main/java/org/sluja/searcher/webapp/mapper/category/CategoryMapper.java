package org.sluja.searcher.webapp.mapper.category;

import org.sluja.searcher.webapp.dto.presentation.category.CategoryDto;
import org.sluja.searcher.webapp.model.category.Category;
import org.sluja.searcher.webapp.model.context.Context;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CategoryMapper {

    public CategoryDto map(final Category category) {
        return new CategoryDto(category.getId(),
                category.getName(),
                getContextsNames(category.getContexts()));
    }

    private List<String> getContextsNames(final List<Context> contexts) {
        return contexts
                .stream()
                .map(Context::getName)
                .toList();
    }
}