package org.sluja.searcher.webapp.exception.scraper.dynamic;

import org.sluja.searcher.webapp.exception.scraper.ScraperIncorrectFieldException;

public class DynamicWebsiteScraperIncorrectFieldException extends ScraperIncorrectFieldException {
    public DynamicWebsiteScraperIncorrectFieldException() {
        super("error.scraper.dynamic.incorrect.field", 11002L);
    }
}
