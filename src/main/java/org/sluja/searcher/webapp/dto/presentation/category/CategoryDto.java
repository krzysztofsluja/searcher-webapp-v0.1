package org.sluja.searcher.webapp.dto.presentation.category;

import jakarta.validation.constraints.NotEmpty;

import java.io.Serializable;
import java.util.List;

/**
 * DTO for {@link org.sluja.searcher.webapp.model.category.Category}
 */
public record CategoryDto(Long id,
                          @NotEmpty String name,
                          List<String> contextNames) implements Serializable {
}