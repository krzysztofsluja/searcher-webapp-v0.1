package org.sluja.searcher.webapp.exception.product;

import org.sluja.searcher.webapp.exception.ExceptionWithErrorCodeAndMessage;

public class TooManyProductPagesException extends ExceptionWithErrorCodeAndMessage {
    public TooManyProductPagesException() {
        super("error.product.too.many.pages", 3001L);
    }
}
