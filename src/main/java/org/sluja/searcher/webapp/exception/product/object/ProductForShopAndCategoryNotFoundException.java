package org.sluja.searcher.webapp.exception.product.object;

import org.sluja.searcher.webapp.exception.ParametrizedExceptionWithErrorCodeAndMessage;
import org.sluja.searcher.webapp.exception.product.general.ProductNotFoundException;
import oshi.util.tuples.Pair;

import java.util.List;

public class ProductForShopAndCategoryNotFoundException extends ProductNotFoundException {

    public ProductForShopAndCategoryNotFoundException(final Pair<String, String> shopWithCategory) {
        super(ParametrizedExceptionWithErrorCodeAndMessage.getCombinedMessage("error.product.for.shop.and.category.not.found", List.of(shopWithCategory.getA(), shopWithCategory.getB())), 3007L);
    }

}
