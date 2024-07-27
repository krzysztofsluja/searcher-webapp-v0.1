package org.sluja.searcher.webapp.exception.message;

import org.sluja.searcher.webapp.exception.ParametrizedExceptionWithErrorCodeAndMessage;

public class MessageForGivenKeyNotFoundException extends ParametrizedExceptionWithErrorCodeAndMessage {
    public MessageForGivenKeyNotFoundException(final String key) {
        super(STR."error.message.not.found|\{key}", 9002L, "|");
    }
}
