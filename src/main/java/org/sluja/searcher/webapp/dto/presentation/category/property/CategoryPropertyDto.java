package org.sluja.searcher.webapp.dto.presentation.category.property;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * DTO for {@link org.sluja.searcher.webapp.model.category.property.CategoryProperty}
 */
@Getter
@Setter
@AllArgsConstructor
public class CategoryPropertyDto implements Serializable {

    private Long id;
    @NotEmpty(message = "error.validation.category.property.value.empty")
    private String value;
    @NotEmpty(message = "error.validation.category.name.empty")
    private String categoryName;
    @NotEmpty(message = "error.validation.context.empty")
    private String context;
}