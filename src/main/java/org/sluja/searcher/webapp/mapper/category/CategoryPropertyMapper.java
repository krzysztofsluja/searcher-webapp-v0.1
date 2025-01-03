package org.sluja.searcher.webapp.mapper.category;

import org.mapstruct.*;
import org.sluja.searcher.webapp.annotation.validation.InputValidation;
import org.sluja.searcher.webapp.dto.presentation.category.property.CategoryPropertyDto;
import org.sluja.searcher.webapp.model.category.property.CategoryProperty;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface CategoryPropertyMapper {

    @Mapping(source = "categoryProperty.category.name", target = "categoryName")
    CategoryPropertyDto toDto(final CategoryProperty categoryProperty, @org.mapstruct.Context final String context);

    @AfterMapping
    @InputValidation(inputs = {CategoryPropertyDto.class})
    default void setContextNameToDto(@MappingTarget final CategoryPropertyDto categoryPropertyDto, @org.mapstruct.Context final String context) {
        categoryPropertyDto.setContext(context);
    }
}