package org.sluja.searcher.webapp.exception.connection;

import org.sluja.searcher.webapp.exception.ExceptionWithErrorCodeAndMessage;

public class ConnectionTimeoutException extends ExceptionWithErrorCodeAndMessage {
    public ConnectionTimeoutException() {
        super("error.scraper.connection.timeout", 2001L);
    }
}
