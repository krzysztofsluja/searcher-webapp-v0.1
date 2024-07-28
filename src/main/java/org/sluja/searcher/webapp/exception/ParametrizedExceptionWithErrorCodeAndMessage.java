package org.sluja.searcher.webapp.exception;

import java.util.List;

public class ParametrizedExceptionWithErrorCodeAndMessage extends ExceptionWithErrorCodeAndMessage {

    public List<String> messageCodeElements;
    public static final String SEPARATOR = "%";
    public static final String VALIDATION_REGEX = "^error\\..*\\%.*";
    public static String getCombinedMessage(final String messageCode, final List<String> parameteres) {
        StringBuilder message = new StringBuilder();
        message.append(messageCode);
        message.append(SEPARATOR);
        parameteres.forEach(parameter -> message.append(parameter).append(SEPARATOR));
        return message.toString();
    }
    public ParametrizedExceptionWithErrorCodeAndMessage(final String messageCode, final List<String> parameters, final Long errorCode) {
        super(getCombinedMessage(messageCode, parameters), errorCode);
        messageCodeElements = parameters;
    }
}
