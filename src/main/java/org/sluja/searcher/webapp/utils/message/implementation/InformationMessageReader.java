package org.sluja.searcher.webapp.utils.message.implementation;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.sluja.searcher.webapp.exception.message.IncorrectMessageCodeForReaderException;
import org.sluja.searcher.webapp.exception.message.MessageForGivenKeyNotFoundException;
import org.sluja.searcher.webapp.utils.logger.LoggerMessageUtils;
import org.sluja.searcher.webapp.utils.logger.LoggerUtils;
import org.sluja.searcher.webapp.utils.message.MessageReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@PropertySource("classpath:info_messages.properties")
@Component
@Slf4j
public class InformationMessageReader extends MessageReader {

    private final String INFORMATION_MESSAGE_PREFIX = "info.";
    private final LoggerMessageUtils loggerMessageUtils;

    @Autowired
    public InformationMessageReader(final Environment environment, final LoggerMessageUtils loggerMessageUtils) {
        super(environment);
        this.loggerMessageUtils = loggerMessageUtils;
    }

    @Override
    public String getPropertyValue(String key) throws IncorrectMessageCodeForReaderException, MessageForGivenKeyNotFoundException {
        return this.getPropertyValue(INFORMATION_MESSAGE_PREFIX, key);
    }

    public String getPropertyValueOrEmptyOnNotFound(final String key) {
        try {
            return getPropertyValue(key);
        } catch (MessageForGivenKeyNotFoundException | IncorrectMessageCodeForReaderException e) {
            log.error(loggerMessageUtils.getErrorLogMessage(LoggerUtils.getCurrentClassName(),
                    LoggerUtils.getCurrentMethodName(),
                    e.getMessageCode(),
                    e.getErrorCode()));
            return StringUtils.EMPTY;
        }
    }
}
