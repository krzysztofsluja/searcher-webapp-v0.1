package org.sluja.searcher.webapp.exception.product;

import org.sluja.searcher.webapp.exception.ExceptionWithErrorCodeAndMessage;

public class ProductNotFoundException extends ExceptionWithErrorCodeAndMessage {
    public ProductNotFoundException() {
        super("error.product.not.found", 3002L);
    }
}
