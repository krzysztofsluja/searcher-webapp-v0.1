package org.sluja.searcher.webapp.exception.product.category;

import org.sluja.searcher.webapp.exception.product.general.ParametrizedProductNotFoundException;
import org.sluja.searcher.webapp.exception.product.general.ProductNotFoundException;

import java.util.List;

public class NoMoreCategoryPageAddressFoundException extends ParametrizedProductNotFoundException {
    public NoMoreCategoryPageAddressFoundException(final String shopName, final String categoryName) {
        super("error.product.category.no.more.page", List.of(shopName, categoryName), 3010L);
    }
}
