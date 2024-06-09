package org.sluja.searcher.webapp.service.scraper;

import org.sluja.searcher.webapp.exception.connection.ConnectionTimeoutException;

import java.io.IOException;
import java.util.List;

public interface Scraper<T,S> {
    T scrapElements(final S attribute, final List<String> addresses) throws ConnectionTimeoutException, IOException;
    T scrapElementsFromPage(final String pageUrl, final String attributeName) throws ConnectionTimeoutException, IOException;
    String scrapElementFromPageBySpecificAttribute(final String pageUrl, final String selectAttributeName, final String attributeName) throws ConnectionTimeoutException, IOException;
}
