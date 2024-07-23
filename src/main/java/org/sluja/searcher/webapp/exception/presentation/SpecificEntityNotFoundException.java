package org.sluja.searcher.webapp.exception.presentation;

import org.sluja.searcher.webapp.exception.ExceptionWithErrorCodeAndMessage;

public abstract class SpecificEntityNotFoundException extends ExceptionWithErrorCodeAndMessage {

    public SpecificEntityNotFoundException(String messageCode, Long errorCode) {
        super(messageCode, errorCode);
    }
}
