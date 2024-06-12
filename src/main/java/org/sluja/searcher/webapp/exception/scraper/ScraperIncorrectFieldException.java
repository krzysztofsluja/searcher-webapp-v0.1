package org.sluja.searcher.webapp.exception.scraper;

import org.sluja.searcher.webapp.exception.ExceptionWithErrorCodeAndMessage;

public class ScraperIncorrectFieldException extends ExceptionWithErrorCodeAndMessage {
    public ScraperIncorrectFieldException(final String messageCode, final Long errorCode) {
        super(messageCode, errorCode);
    }
}
