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
    @NotEmpty
    private String value;
    @NotEmpty
    private String categoryName;
    @NotEmpty
    private String context;
}