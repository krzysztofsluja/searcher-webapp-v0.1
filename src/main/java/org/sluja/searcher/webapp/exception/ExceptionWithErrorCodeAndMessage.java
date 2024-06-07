package org.sluja.searcher.webapp.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ExceptionWithErrorCodeAndMessage extends Exception {
    public final String messageCode;
    public final Long errorCode;
    public final static Long GENERAL_ERROR_CODE = 10000L;
    public final static String GENERAL_MESSAGE_CODE = "error.general";
}
