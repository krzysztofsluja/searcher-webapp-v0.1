package org.sluja.searcher.webapp.service.interfaces.search;

import org.sluja.searcher.webapp.dto.product.request.search.SearchServiceRequest;
import org.sluja.searcher.webapp.dto.scraper.ScrapRequest;
import org.sluja.searcher.webapp.exception.enums.search.ValueForSearchPropertyException;
import org.sluja.searcher.webapp.exception.product.general.ProductNotFoundException;
import org.sluja.searcher.webapp.exception.scraper.ScraperIncorrectFieldException;
import org.sluja.searcher.webapp.service.interfaces.scrap.IGetScraper;

import java.util.List;

public interface ISearch<S extends SearchServiceRequest,T extends ScrapRequest> extends IGetScraper {

    default List<?> search(final T scrapRequest) throws ScraperIncorrectFieldException {
        return (List<?>) getScraperService().scrap(scrapRequest);
    }

    default List<?> search(S searchRequest, T scrapRequest) throws ValueForSearchPropertyException, ProductNotFoundException, ScraperIncorrectFieldException {
        //TODO
        throw new UnsupportedOperationException("The search method returning List with input of search request and scrap request is not supported in that class.");
    }
}
