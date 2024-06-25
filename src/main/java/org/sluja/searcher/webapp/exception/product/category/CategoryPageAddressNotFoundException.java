package org.sluja.searcher.webapp.exception.product.category;

import org.sluja.searcher.webapp.exception.ExceptionWithErrorCodeAndMessage;
import org.sluja.searcher.webapp.exception.product.general.ProductNotFoundException;

public class CategoryPageAddressNotFoundException extends ProductNotFoundException {
    public CategoryPageAddressNotFoundException() {
        super("error.product.category.not.found", 3003L);
    }
}
