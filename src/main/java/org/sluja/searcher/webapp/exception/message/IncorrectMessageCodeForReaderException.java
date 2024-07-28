package org.sluja.searcher.webapp.exception.message;

import org.sluja.searcher.webapp.exception.ParametrizedExceptionWithErrorCodeAndMessage;

import java.util.List;

public class IncorrectMessageCodeForReaderException extends ParametrizedExceptionWithErrorCodeAndMessage {
    public IncorrectMessageCodeForReaderException(final String propertyName) {
        super("error.message.reader.incorrect.message.code", List.of(propertyName),  9001L);
    }
}
