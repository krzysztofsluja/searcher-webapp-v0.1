package org.sluja.searcher.webapp.exception.presentation.category.property;

import org.sluja.searcher.webapp.exception.presentation.SpecificEntityNotFoundException;

public class PropertyForCategoryNotFoundException extends SpecificEntityNotFoundException {
    public PropertyForCategoryNotFoundException() {
        super("error.entity.category.property.not.found", 8002L);
    }
}
