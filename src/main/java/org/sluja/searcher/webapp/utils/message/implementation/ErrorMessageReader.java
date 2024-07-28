package org.sluja.searcher.webapp.utils.message.implementation;

import org.sluja.searcher.webapp.exception.ExceptionWithErrorCodeAndMessage;
import org.sluja.searcher.webapp.exception.message.IncorrectMessageCodeForReaderException;
import org.sluja.searcher.webapp.exception.message.MessageForGivenKeyNotFoundException;
import org.sluja.searcher.webapp.utils.message.MessageReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@PropertySource("classpath:error_messages.properties")
@Component
public class ErrorMessageReader extends MessageReader {

    private final String ERROR_MESSAGE_PREFIX = "error.";
    private final Environment environment;
    @Autowired
    public ErrorMessageReader(final Environment environment) {
        super(environment);
        this.environment = environment;
    }

    @Override
    public String getPropertyValue(final String key) throws IncorrectMessageCodeForReaderException, MessageForGivenKeyNotFoundException {
        return this.getPropertyValue(ERROR_MESSAGE_PREFIX, key);

    }

    public String getPropertyValueOrGeneralMessageOnDefault(final String key) {
        try {
            return getPropertyValue(key);
        } catch (final IncorrectMessageCodeForReaderException | MessageForGivenKeyNotFoundException e) {
            return environment.getProperty(ExceptionWithErrorCodeAndMessage.GENERAL_MESSAGE_CODE);
        }
    }

}
