package org.sluja.searcher.webapp.mapper.category;

import jakarta.validation.Valid;
import org.mapstruct.*;
import org.sluja.searcher.webapp.dto.presentation.category.property.CategoryPropertyDto;
import org.sluja.searcher.webapp.model.category.property.CategoryProperty;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface CategoryPropertyMapper {

    @Mapping(source = "categoryProperty.category.name", target = "categoryName")
    CategoryPropertyDto toDto(final CategoryProperty categoryProperty, @org.mapstruct.Context final String context);

    @AfterMapping
    default void setContextNameToDto(@Valid @MappingTarget final CategoryPropertyDto categoryPropertyDto, @org.mapstruct.Context final String context) {
        categoryPropertyDto.setContext(context);
    }
}