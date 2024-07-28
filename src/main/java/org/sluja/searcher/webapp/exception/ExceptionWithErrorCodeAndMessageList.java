package org.sluja.searcher.webapp.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class ExceptionWithErrorCodeAndMessageList extends Exception {

    private final List<String> messageCodes;
    private final Long errorCode;
    public final static Long GENERAL_ERROR_CODE = 10000L;
    public final static String GENERAL_MESSAGE_CODE = "error.general";
}
