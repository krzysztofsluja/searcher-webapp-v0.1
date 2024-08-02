package org.sluja.searcher.webapp.utils.message.builder;

import org.sluja.searcher.webapp.exception.ParametrizedExceptionWithErrorCodeAndMessage;

import java.util.List;

public class InformationMessageBuilder {

    public static String buildParametrizedMessage(final String messageCode, final List<String> parameters) {
        final StringBuilder builder = new StringBuilder(messageCode);
        parameters.forEach(parameter -> builder.append(ParametrizedExceptionWithErrorCodeAndMessage.SEPARATOR).append(parameter));
        return builder.toString();
    }
}
