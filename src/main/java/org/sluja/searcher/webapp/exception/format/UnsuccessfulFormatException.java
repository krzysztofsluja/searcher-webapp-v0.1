package org.sluja.searcher.webapp.exception.format;

import org.sluja.searcher.webapp.exception.ExceptionWithErrorCodeAndMessage;

public class UnsuccessfulFormatException extends ExceptionWithErrorCodeAndMessage {
    public UnsuccessfulFormatException() {
        super("error.product.unsuccessful.format", 4001L);
    }
}
