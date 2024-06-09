package org.sluja.searcher.webapp.service.scraper;

import org.sluja.searcher.webapp.exception.connection.ConnectionTimeoutException;
import org.sluja.searcher.webapp.exception.product.ProductNotFoundException;

import java.io.IOException;
import java.util.List;
public interface Scraper<T,S> {
    List<T> scrap(final String pageUrl, final String property) throws ProductNotFoundException;
    T scrap(final T element, final String property, final S elementClass) throws ProductNotFoundException;
}
