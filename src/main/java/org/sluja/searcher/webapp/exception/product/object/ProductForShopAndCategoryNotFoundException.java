package org.sluja.searcher.webapp.exception.product.object;

import org.sluja.searcher.webapp.exception.product.general.ParametrizedProductNotFoundException;
import oshi.util.tuples.Pair;

public class ProductForShopAndCategoryNotFoundException extends ParametrizedProductNotFoundException {

    public ProductForShopAndCategoryNotFoundException(final Pair<String, String> shopWithCategory) {
        super(new StringBuilder("error.product.for.shop.and.category.not.found")
                .append("|")
                .append(shopWithCategory.getA())
                .append("|")
                .append(shopWithCategory.getB())
                .toString(), 3007L, "|");
    }

}
