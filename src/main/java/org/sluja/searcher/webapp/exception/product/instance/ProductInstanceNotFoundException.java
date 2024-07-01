package org.sluja.searcher.webapp.exception.product.instance;

import org.sluja.searcher.webapp.exception.product.general.ProductNotFoundException;

public class ProductInstanceNotFoundException extends ProductNotFoundException {
    public ProductInstanceNotFoundException() {
        super("error.product.instance.not.found", 3006L);
    }
}
