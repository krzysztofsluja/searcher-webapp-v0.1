package org.sluja.searcher.webapp.utils.message;

import org.apache.commons.lang3.StringUtils;
import org.sluja.searcher.webapp.exception.message.IncorrectMessageCodeForReaderException;
import org.sluja.searcher.webapp.exception.message.MessageForGivenKeyNotFoundException;
import org.springframework.core.env.Environment;

public abstract class MessageReader {

    final Environment environment;
    public MessageReader(final Environment environment) {
        this.environment = environment;
    }

    protected String getPropertyValue(final String prefix, final String key) throws IncorrectMessageCodeForReaderException, MessageForGivenKeyNotFoundException {
        if(key.startsWith(prefix)) {
            final String message = environment.getProperty(key);
            if(StringUtils.isNotEmpty(message)) {
                return message;
            }
            throw new MessageForGivenKeyNotFoundException(key);
        }
        throw new IncorrectMessageCodeForReaderException(key);
    }

    public abstract String getPropertyValue(final String key) throws IncorrectMessageCodeForReaderException, MessageForGivenKeyNotFoundException;
}
