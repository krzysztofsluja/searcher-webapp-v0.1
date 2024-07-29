package org.sluja.searcher.webapp.utils.logger;

import lombok.RequiredArgsConstructor;
import org.sluja.searcher.webapp.utils.message.implementation.ErrorMessageReader;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LoggerMessageUtils {

    private final ErrorMessageReader errorMessageReader;
    private final String SEPARATOR = "|||";

    public String getErrorLogMessage(final String className, final String methodName, final String errorMessageCode, final Long errorCode) {
        final String messageText = errorMessageReader.getPropertyValueOrGeneralMessageOnDefault(errorMessageCode);
        return new StringBuilder("|ERROR")
                .append(SEPARATOR)
                .append(className)
                .append(SEPARATOR)
                .append(methodName)
                .append(SEPARATOR)
                .append(errorCode)
                .append(SEPARATOR)
                .append(messageText)
                .toString();

    }

    public String getInfoLogMessage(final String className, final String methodName, final String text) {
        return new StringBuilder()
                .append("|INFO")
                .append(SEPARATOR)
                .append(className)
                .append(SEPARATOR)
                .append(methodName)
                .append(SEPARATOR)
                .append(text)
                .toString();
    }
}
