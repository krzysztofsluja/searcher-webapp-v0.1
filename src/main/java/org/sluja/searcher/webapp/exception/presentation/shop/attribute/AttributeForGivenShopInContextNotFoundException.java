package org.sluja.searcher.webapp.exception.presentation.shop.attribute;

import org.sluja.searcher.webapp.exception.presentation.SpecificEntityNotFoundException;

public class AttributeForGivenShopInContextNotFoundException extends SpecificEntityNotFoundException {
    public AttributeForGivenShopInContextNotFoundException() {
        super("error.entity.shop.attribute.not.found", 8003L);
    }
}
