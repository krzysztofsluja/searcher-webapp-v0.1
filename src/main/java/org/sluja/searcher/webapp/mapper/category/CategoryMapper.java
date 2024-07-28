package org.sluja.searcher.webapp.mapper.category;

import jakarta.validation.Valid;
import org.mapstruct.*;
import org.sluja.searcher.webapp.annotation.validation.InputValidation;
import org.sluja.searcher.webapp.dto.presentation.category.CategoryDto;
import org.sluja.searcher.webapp.model.category.Category;
import org.sluja.searcher.webapp.model.context.Context;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    @InputValidation(inputs = CategoryDto.class)
    Category toEntity(CategoryDto categoryDto);

    @Mapping(target = "contextNames", expression = "java(contextsToContextNames(category.getContexts()))")
    CategoryDto toDto(Category category);

    default List<String> contextsToContextNames(List<org.sluja.searcher.webapp.model.context.Context> contexts) {
        return contexts.stream().map(Context::getName).collect(Collectors.toList());
    }
}