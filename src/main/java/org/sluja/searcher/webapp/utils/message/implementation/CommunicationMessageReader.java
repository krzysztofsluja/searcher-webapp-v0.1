package org.sluja.searcher.webapp.utils.message.implementation;

import lombok.extern.slf4j.Slf4j;
import org.sluja.searcher.webapp.exception.message.IncorrectMessageCodeForReaderException;
import org.sluja.searcher.webapp.exception.message.MessageForGivenKeyNotFoundException;
import org.sluja.searcher.webapp.utils.message.MessageReader;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@PropertySource("classpath:communication_messages.properties")
@Component
@Slf4j
@Qualifier("communicationMessageReader")
public class CommunicationMessageReader extends MessageReader {

    private final String COMMUNICATION_MESSAGE_PREFIX = "communication.";

    public CommunicationMessageReader(Environment environment) {
        super(environment);
    }

    @Override
    public String getPropertyValue(final String key) throws IncorrectMessageCodeForReaderException, MessageForGivenKeyNotFoundException {
        return this.getPropertyValue(COMMUNICATION_MESSAGE_PREFIX, key);
    }
}
