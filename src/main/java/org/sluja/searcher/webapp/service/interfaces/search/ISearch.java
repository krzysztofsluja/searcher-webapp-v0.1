package org.sluja.searcher.webapp.service.interfaces.search;

import org.sluja.searcher.webapp.dto.product.request.search.SearchServiceRequest;
import org.sluja.searcher.webapp.dto.scraper.ScrapRequest;
import org.sluja.searcher.webapp.exception.enums.search.ValueForSearchPropertyException;
import org.sluja.searcher.webapp.exception.product.general.ProductNotFoundException;
import org.sluja.searcher.webapp.exception.scraper.ScraperIncorrectFieldException;
import org.sluja.searcher.webapp.service.factory.scraper.WebsiteScraperFactory;
import org.sluja.searcher.webapp.service.scraper.interfaces.WebsiteScraper;

import java.util.List;

public interface ISearch<S extends SearchServiceRequest,T extends ScrapRequest> {

    default List<?> search(final boolean isDynamicWebsite, T scrapRequest) throws ValueForSearchPropertyException, ProductNotFoundException, ScraperIncorrectFieldException {
        final WebsiteScraper scraperService = WebsiteScraperFactory.getScraper(isDynamicWebsite);
        return (List<?>) scraperService.scrap(scrapRequest);
    }

    default List<?> search(S searchRequest, T scrapRequest) throws ValueForSearchPropertyException, ProductNotFoundException, ScraperIncorrectFieldException {
        //TODO
        return null;
    }
}
