package org.sluja.searcher.webapp.exception.enums.search;

import org.sluja.searcher.webapp.exception.ExceptionWithErrorCodeAndMessage;

public class ValueForSearchPropertyException extends ExceptionWithErrorCodeAndMessage {
    public ValueForSearchPropertyException() {
        super("Value for search property is not valid.",10001L);
    }
}
