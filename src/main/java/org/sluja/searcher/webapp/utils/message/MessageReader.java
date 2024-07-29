package org.sluja.searcher.webapp.utils.message;

import org.apache.commons.lang3.StringUtils;
import org.sluja.searcher.webapp.exception.ParametrizedExceptionWithErrorCodeAndMessage;
import org.sluja.searcher.webapp.exception.message.IncorrectMessageCodeForReaderException;
import org.sluja.searcher.webapp.exception.message.MessageForGivenKeyNotFoundException;
import org.springframework.core.env.Environment;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public abstract class MessageReader {

    final Environment environment;
    public MessageReader(final Environment environment) {
        this.environment = environment;
    }

    protected String getPropertyValue(final String prefix, final String key) throws IncorrectMessageCodeForReaderException, MessageForGivenKeyNotFoundException {
        if(key.startsWith(prefix)) {
            if(StringUtils.isNotEmpty(key)) {
                return getMessage(key);
            }
            throw new MessageForGivenKeyNotFoundException(key);
        }
        throw new IncorrectMessageCodeForReaderException(key);
    }

    protected String getKeyForPropertiesFile(final String messageCode) {
        return messageCode.substring(0, messageCode.indexOf(ParametrizedExceptionWithErrorCodeAndMessage.SEPARATOR));
    }

    protected String getMessage(final String message) {
        if(isMessageParametrized(message)) {
            return getParametrizedMessage(message);
        }
        return message;
    }

    protected boolean isMessageParametrized(final String message) {
        final Pattern pattern = Pattern.compile(ParametrizedExceptionWithErrorCodeAndMessage.VALIDATION_REGEX);
        return pattern.matcher(message).matches();
    }

    protected String getParametrizedMessage(final String message) {
        final String[] messageElements = message.split(ParametrizedExceptionWithErrorCodeAndMessage.SEPARATOR);
        final List<String> messageParameters =  Arrays.stream(messageElements)
                .skip(1)
                .toList();
        return String.format(environment.getProperty(getKeyForPropertiesFile(message)), messageParameters.toArray());
    }

    public abstract String getPropertyValue(final String key) throws IncorrectMessageCodeForReaderException, MessageForGivenKeyNotFoundException;
}
