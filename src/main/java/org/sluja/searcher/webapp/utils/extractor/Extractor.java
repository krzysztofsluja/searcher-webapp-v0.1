package org.sluja.searcher.webapp.utils.extractor;

import org.sluja.searcher.webapp.exception.format.UnsuccessfulFormatException;
import org.sluja.searcher.webapp.exception.product.general.ProductNotFoundException;
import org.sluja.searcher.webapp.exception.scraper.ScraperIncorrectFieldException;

@FunctionalInterface
public interface Extractor<E,V,T> {
    E extract(final V object, final T objectTwo) throws UnsuccessfulFormatException, ProductNotFoundException, ScraperIncorrectFieldException;
}
