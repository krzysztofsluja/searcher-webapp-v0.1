package org.sluja.searcher.webapp.exception.product.shop;

import org.sluja.searcher.webapp.exception.product.general.ProductNotFoundException;

public class ShopCategoriesPageAddressesNotFoundException extends ProductNotFoundException {
    public ShopCategoriesPageAddressesNotFoundException() {
        super("error.product.shop.categories.page.addresses.not.found", 3004L);
    }
}
