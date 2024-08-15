package org.sluja.searcher.webapp.dto.context;

import jakarta.validation.constraints.NotEmpty;
import org.sluja.searcher.webapp.utils.dto.validation.DtoValidationErrorMessage;

import java.io.Serializable;

/**
 * DTO for {@link org.sluja.searcher.webapp.model.context.Context}
 */
public record ContextDto(Long id,
                         @NotEmpty(message = DtoValidationErrorMessage.CONTEXT_EMPTY) String name) implements Serializable {
}