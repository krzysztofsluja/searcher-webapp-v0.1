package org.sluja.searcher.webapp.utils.logger;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.sluja.searcher.webapp.utils.message.implementation.ErrorMessageReader;
import org.sluja.searcher.webapp.utils.message.implementation.InformationMessageReader;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class LoggerMessageUtils {

    private final ErrorMessageReader errorMessageReader;
    private final InformationMessageReader informationMessageReader;
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

    public String getErrorLogMessageWithDefaultErrorCode(final String className, final String methodName, final String errorMessageCode) {
        return getErrorLogMessage(className, methodName, errorMessageCode, -1L);
    }

    public String getErrorLogMessageWithDeclaredErrorMessage(final String className, final String methodName, final String errorMessage) {
        return new StringBuilder("|ERROR")
                .append(SEPARATOR)
                .append(className)
                .append(SEPARATOR)
                .append(methodName)
                .append(SEPARATOR)
                .append(-1L)
                .append(SEPARATOR)
                .append(errorMessage)
                .toString();
    }

    public String getInfoLogMessage(final String className, final String methodName, final String messageCode) {
        final String messageText = informationMessageReader.getPropertyValueOrEmptyOnNotFound(messageCode);
        return new StringBuilder()
                .append("|INFO")
                .append(SEPARATOR)
                .append(className)
                .append(SEPARATOR)
                .append(methodName)
                .append(SEPARATOR)
                .append(messageText)
                .toString();
    }

    public String getInfoLogMessageWithObjects(final String className, final String methodName, final String messageCode, final Object[] objects) {
        final String messageText = informationMessageReader.getPropertyValueOrEmptyOnNotFound(messageCode);
        return new StringBuilder()
                .append("|INFO")
                .append(SEPARATOR)
                .append(className)
                .append(SEPARATOR)
                .append(methodName)
                .append(SEPARATOR)
                .append(messageText)
                .append(StringUtils.SPACE)
                .append(Arrays.stream(objects).map(Object::toString).collect(Collectors.joining(",")))
                .toString();
    }
}
