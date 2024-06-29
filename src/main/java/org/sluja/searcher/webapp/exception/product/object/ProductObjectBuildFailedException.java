package org.sluja.searcher.webapp.exception.product.object;

import org.sluja.searcher.webapp.exception.product.general.ProductNotFoundException;

public class ProductObjectBuildFailedException extends ProductNotFoundException {
    public ProductObjectBuildFailedException() {
        super("error.product.object.build.failed", 3005L);
    }
}
