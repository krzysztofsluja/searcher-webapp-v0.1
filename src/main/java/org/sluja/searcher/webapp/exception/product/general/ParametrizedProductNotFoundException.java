package org.sluja.searcher.webapp.exception.product.general;

import org.sluja.searcher.webapp.exception.ParametrizedExceptionWithErrorCodeAndMessage;

public abstract class ParametrizedProductNotFoundException extends ParametrizedExceptionWithErrorCodeAndMessage {
    public ParametrizedProductNotFoundException(final String messageCode, final Long errorCode, final String separator) {
        super(messageCode, errorCode, separator);
    }
}
