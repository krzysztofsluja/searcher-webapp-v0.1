package org.sluja.searcher.webapp.exception.product.shop;

import org.sluja.searcher.webapp.exception.product.general.ParametrizedProductNotFoundException;

import java.util.List;

public class ShopCategoriesPageAddressesNotFoundException extends ParametrizedProductNotFoundException {
    public ShopCategoriesPageAddressesNotFoundException(final String shopName) {
        super("error.product.shop.categories.page.addresses.not.found", List.of(shopName),3004L);
    }
}
