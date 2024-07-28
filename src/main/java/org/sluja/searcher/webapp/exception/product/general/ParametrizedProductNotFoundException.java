package org.sluja.searcher.webapp.exception.product.general;

import org.sluja.searcher.webapp.exception.ParametrizedExceptionWithErrorCodeAndMessage;

import java.util.List;

public abstract class ParametrizedProductNotFoundException extends ProductNotFoundException {
    public ParametrizedProductNotFoundException(final String messageCode, final List<String> parameters, final Long errorCode) {
        super(ParametrizedExceptionWithErrorCodeAndMessage.getCombinedMessage(messageCode, parameters), errorCode);
    }
}
