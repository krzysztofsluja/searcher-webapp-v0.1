package org.sluja.searcher.webapp.utils.message.implementation;

import org.sluja.searcher.webapp.exception.message.IncorrectMessageCodeForReaderException;
import org.sluja.searcher.webapp.exception.message.MessageForGivenKeyNotFoundException;
import org.sluja.searcher.webapp.utils.message.MessageReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@PropertySource("classpath:info_messages.properties")
@Component
public class InformationMessageReader extends MessageReader {

    private final String INFORMATION_MESSAGE_PREFIX = "info.";
    @Autowired
    public InformationMessageReader(final Environment environment) {
        super(environment);
    }

    @Override
    public String getPropertyValue(String key) throws IncorrectMessageCodeForReaderException, MessageForGivenKeyNotFoundException {
        return this.getPropertyValue(INFORMATION_MESSAGE_PREFIX, key);
    }
}
