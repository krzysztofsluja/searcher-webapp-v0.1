package org.sluja.searcher.webapp.dto.presentation.category.property;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.sluja.searcher.webapp.utils.dto.validation.DtoValidationErrorMessage;

import java.io.Serializable;

/**
 * DTO for {@link org.sluja.searcher.webapp.model.category.property.CategoryProperty}
 */
@Getter
@Setter
@AllArgsConstructor
public class CategoryPropertyDto implements Serializable {

    private Long id;
    @NotEmpty(message = DtoValidationErrorMessage.CATEGORY_PROPERTY_VALUE_EMPTY)
    private String value;
    @NotEmpty(message = DtoValidationErrorMessage.CATEGORY_NAME_EMPTY)
    private String categoryName;
    @NotEmpty(message = DtoValidationErrorMessage.CONTEXT_EMPTY)
    private String context;
}