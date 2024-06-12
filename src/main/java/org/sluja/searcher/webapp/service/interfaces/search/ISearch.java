package org.sluja.searcher.webapp.service.interfaces.search;

import org.sluja.searcher.webapp.dto.scraper.ScrapRequest;
import org.sluja.searcher.webapp.dto.scraper.search.SearchRequest;
import org.sluja.searcher.webapp.exception.enums.search.ValueForSearchPropertyException;
import org.sluja.searcher.webapp.exception.product.ProductNotFoundException;
import org.sluja.searcher.webapp.exception.scraper.ScraperIncorrectFieldException;

import java.util.List;

public interface ISearch<T extends ScrapRequest> {

    List<?> search(SearchRequest request, T scrapRequest) throws ValueForSearchPropertyException, ProductNotFoundException, ScraperIncorrectFieldException;
}
