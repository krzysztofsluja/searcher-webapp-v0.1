package org.sluja.searcher.webapp.exception.product.general;

import org.sluja.searcher.webapp.exception.ExceptionWithErrorCodeAndMessage;

public abstract class ProductNotFoundException extends ExceptionWithErrorCodeAndMessage {
    public ProductNotFoundException(final String messageCode, final Long errorCode) {
        super(messageCode, errorCode);
    }
}
