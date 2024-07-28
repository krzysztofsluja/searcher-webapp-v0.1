package org.sluja.searcher.webapp.exception.validation;

import org.sluja.searcher.webapp.exception.ExceptionWithErrorCodeAndMessageList;

import java.util.List;

public class ValidationNotPassedException extends ExceptionWithErrorCodeAndMessageList {
    public ValidationNotPassedException(final List<String> messageCodes) {
        super(messageCodes, 13001L);
    }
}
