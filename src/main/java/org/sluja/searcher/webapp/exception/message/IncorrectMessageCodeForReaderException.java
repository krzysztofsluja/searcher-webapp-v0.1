package org.sluja.searcher.webapp.exception.message;

import org.sluja.searcher.webapp.exception.ParametrizedExceptionWithErrorCodeAndMessage;

public class IncorrectMessageCodeForReaderException extends ParametrizedExceptionWithErrorCodeAndMessage {
    public IncorrectMessageCodeForReaderException(final String propertyName) {
        super(STR."error.message.reader.incorrect.message.code|\{propertyName}", 9001L, "|");
    }
}
