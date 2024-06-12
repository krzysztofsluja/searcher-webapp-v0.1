package org.sluja.searcher.webapp.exception.scraper.stat;

import org.sluja.searcher.webapp.exception.ExceptionWithErrorCodeAndMessage;
import org.sluja.searcher.webapp.exception.scraper.ScraperIncorrectFieldException;

public class StaticWebsiteScraperIncorrectFieldException extends ScraperIncorrectFieldException {
    public StaticWebsiteScraperIncorrectFieldException() {
        super("error.scraper.static.incorrect.field", 11001L);
    }
}
