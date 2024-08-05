package org.sluja.searcher.webapp.aspect.exception.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sluja.searcher.webapp.dto.response.ApiResponse;
import org.sluja.searcher.webapp.exception.ExceptionWithErrorCodeAndMessage;
import org.sluja.searcher.webapp.exception.ParametrizedExceptionWithErrorCodeAndMessage;
import org.sluja.searcher.webapp.exception.message.IncorrectMessageCodeForReaderException;
import org.sluja.searcher.webapp.exception.message.MessageForGivenKeyNotFoundException;
import org.sluja.searcher.webapp.exception.validation.ValidationNotPassedException;
import org.sluja.searcher.webapp.utils.logger.LoggerMessageUtils;
import org.sluja.searcher.webapp.utils.logger.LoggerUtils;
import org.sluja.searcher.webapp.utils.message.implementation.ErrorMessageReader;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

@ControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class ValidationExceptionAdvice {

    private final ErrorMessageReader errorMessageReader;
    private final LoggerMessageUtils loggerMessageUtils;
    @ExceptionHandler(ValidationNotPassedException.class)
    public ResponseEntity<ApiResponse<Void>> handleValidationNotPassedException(final ValidationNotPassedException e) {
        log.error(loggerMessageUtils.getErrorLogMessage());
        final List<String> messageCodes = e.getMessageCodes();
        if(messageCodes.contains(ExceptionWithErrorCodeAndMessage.GENERAL_MESSAGE_CODE)) {
            //TODO logging
            return new ResponseEntity<>(new ApiResponse<>(null, List.of(errorMessageReader.getPropertyValueOrGeneralMessageOnDefault(ExceptionWithErrorCodeAndMessage.GENERAL_MESSAGE_CODE))), HttpStatus.BAD_REQUEST);
        }
        final List<String> errorsList = e.getMessageCodes().stream()
                .map(code -> {
                    if(isMessageParametrized(code)) {
                        return getParametrizedMessage(code);
                    }
                    return errorMessageReader.getPropertyValueOrGeneralMessageOnDefault(code);
                })
                .toList();
        return new ResponseEntity<>(new ApiResponse<>(null, errorsList), HttpStatus.BAD_REQUEST);
    }

    private String getParametrizedMessage(final String message) {
        final String[] messageElements = message.split(ParametrizedExceptionWithErrorCodeAndMessage.SEPARATOR);
        final List<String> messageParameters =  Arrays.stream(messageElements)
                .skip(1)
                .toList();
        try {
            return String.format(errorMessageReader.getPropertyValue(messageElements[0]), messageParameters);
        } catch (final IncorrectMessageCodeForReaderException | MessageForGivenKeyNotFoundException e) {
            log.error(loggerMessageUtils.getErrorLogMessage(LoggerUtils.getCurrentClassName(),
                    LoggerUtils.getCurrentMethodName(),
                    e.getMessageCode(),
                    e.getErrorCode()));
            return errorMessageReader.getPropertyValueOrGeneralMessageOnDefault(messageElements[0]);
        }
    }

    private boolean isMessageParametrized(final String message) {
        final Pattern pattern = Pattern.compile(ParametrizedExceptionWithErrorCodeAndMessage.VALIDATION_REGEX);
        return pattern.matcher(message).matches();
    }
}
