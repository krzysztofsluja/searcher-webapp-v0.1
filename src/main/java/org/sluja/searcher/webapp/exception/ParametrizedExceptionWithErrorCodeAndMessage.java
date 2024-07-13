package org.sluja.searcher.webapp.exception;

public class ParametrizedExceptionWithErrorCodeAndMessage extends ExceptionWithErrorCodeAndMessage {
    public ParametrizedExceptionWithErrorCodeAndMessage(final String messageCode, final Long errorCode, final String separator) {
        super(messageCode, errorCode);
        final String[] messageCodeElements = messageCode.split(separator);
    }
}
