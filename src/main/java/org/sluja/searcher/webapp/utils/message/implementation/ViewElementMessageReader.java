package org.sluja.searcher.webapp.utils.message.implementation;

import lombok.extern.slf4j.Slf4j;
import org.sluja.searcher.webapp.exception.message.IncorrectMessageCodeForReaderException;
import org.sluja.searcher.webapp.exception.message.MessageForGivenKeyNotFoundException;
import org.sluja.searcher.webapp.utils.message.MessageReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@PropertySource("classpath:view_messages.properties")
@Component
@Slf4j
@Qualifier("viewElementMessageReader")
public class ViewElementMessageReader extends MessageReader {

    private final String VIEW_MESSAGE_PREFIX = "view.";
    @Autowired
    public ViewElementMessageReader(Environment environment) {
        super(environment);
    }

    @Override
    public String getPropertyValue(String key) throws IncorrectMessageCodeForReaderException, MessageForGivenKeyNotFoundException {
        return this.getPropertyValue(VIEW_MESSAGE_PREFIX, key);
    }
}
