package org.sluja.searcher.webapp.dto.presentation.category;

import jakarta.validation.constraints.NotEmpty;
import org.sluja.searcher.webapp.utils.dto.validation.DtoValidationErrorMessage;

import java.io.Serializable;
import java.util.List;

/**
 * DTO for {@link org.sluja.searcher.webapp.model.category.Category}
 */
public record CategoryDto(Long id,
                          @NotEmpty(message = DtoValidationErrorMessage.CATEGORY_NAME_EMPTY) String name,
                          List<String> contextNames) implements Serializable {
}