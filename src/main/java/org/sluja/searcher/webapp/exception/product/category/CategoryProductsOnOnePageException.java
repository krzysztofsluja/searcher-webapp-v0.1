package org.sluja.searcher.webapp.exception.product.category;

import org.sluja.searcher.webapp.exception.product.general.ProductNotFoundException;

public class CategoryProductsOnOnePageException extends ProductNotFoundException {
    public CategoryProductsOnOnePageException() {
        super("error.product.category.products.on.one.page", 3009L);
    }
}
