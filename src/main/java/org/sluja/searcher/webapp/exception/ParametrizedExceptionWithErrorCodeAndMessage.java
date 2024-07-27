package org.sluja.searcher.webapp.exception;

import java.util.Arrays;
import java.util.List;

public class ParametrizedExceptionWithErrorCodeAndMessage extends ExceptionWithErrorCodeAndMessage {

    public List<String> messageCodeElements;
    public ParametrizedExceptionWithErrorCodeAndMessage(final String messageCode, final Long errorCode, final String separator) {
        super(messageCode.split(separator)[0], errorCode);
        messageCodeElements = Arrays.stream(messageCode.split(separator))
                .skip(1)
                .toList();
    }
}
