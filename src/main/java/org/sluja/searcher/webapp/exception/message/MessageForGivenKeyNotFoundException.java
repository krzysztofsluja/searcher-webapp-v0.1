package org.sluja.searcher.webapp.exception.message;

import org.sluja.searcher.webapp.exception.ParametrizedExceptionWithErrorCodeAndMessage;

import java.util.List;

public class MessageForGivenKeyNotFoundException extends ParametrizedExceptionWithErrorCodeAndMessage {
    public MessageForGivenKeyNotFoundException(final String key) {
        super("error.message.not.found", List.of(key), 9002L);
    }
}
