package org.sluja.searcher.webapp.dto.presentation.category.property;

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
    private String value;
    private String categoryName;
    private String context;
}