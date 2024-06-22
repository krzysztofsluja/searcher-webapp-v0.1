package org.sluja.searcher.webapp.exception.scraper.search;

import org.sluja.searcher.webapp.exception.ExceptionWithErrorCodeAndMessage;

public class IncorrectInputException extends ExceptionWithErrorCodeAndMessage {

    public IncorrectInputException(final String propertyValue) {
        super(String.format("scraper.search.input.incorrect", propertyValue), 12001L);
    }
}
