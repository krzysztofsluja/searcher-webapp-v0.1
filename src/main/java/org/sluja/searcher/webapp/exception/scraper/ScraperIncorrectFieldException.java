package org.sluja.searcher.webapp.exception.scraper;

import org.sluja.searcher.webapp.exception.ExceptionWithErrorCodeAndMessage;

public class ScraperIncorrectFieldException extends ExceptionWithErrorCodeAndMessage {
    public ScraperIncorrectFieldException() {
        super("error.scraper.incorrect.field", 6001L);
    }

    public ScraperIncorrectFieldException(final String errorMessage, final Long errorCode) {
        super(errorMessage, errorCode);
    }
}
