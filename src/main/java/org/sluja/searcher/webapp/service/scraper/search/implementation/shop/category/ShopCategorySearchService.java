package org.sluja.searcher.webapp.service.scraper.search.implementation.shop.category;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.select.Elements;
import org.sluja.searcher.webapp.dto.marker.scraper.IScraper;
import org.sluja.searcher.webapp.dto.scraper.ProductScrapWithDefinedAttributes;
import org.sluja.searcher.webapp.dto.scraper.ScrapRequest;
import org.sluja.searcher.webapp.dto.scraper.search.SearchRequest;
import org.sluja.searcher.webapp.enums.scraper.search.SearchProperty;
import org.sluja.searcher.webapp.exception.connection.ConnectionTimeoutException;
import org.sluja.searcher.webapp.exception.enums.search.ValueForSearchPropertyException;
import org.sluja.searcher.webapp.exception.product.ProductNotFoundException;
import org.sluja.searcher.webapp.exception.scraper.ScraperIncorrectFieldException;
import org.sluja.searcher.webapp.service.enums.search.SearchPropertyGetComponent;
import org.sluja.searcher.webapp.service.factory.scraper.WebsiteScraperFactory;
import org.sluja.searcher.webapp.service.interfaces.search.ISearch;
import org.sluja.searcher.webapp.service.scraper.interfaces.WebsiteScraper;
import org.sluja.searcher.webapp.service.scraper.search.BaseSearchService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class ShopCategorySearchService<T extends ScrapRequest> extends BaseSearchService implements ISearch<T> {

    @Override
    public List<?> search(final SearchRequest request, T scrapRequest) throws ValueForSearchPropertyException, ProductNotFoundException, ScraperIncorrectFieldException {
        final WebsiteScraper scraperService = WebsiteScraperFactory.getScraper(request.dynamicWebsite());
        final List<?> addresses = new ArrayList<>();
        final List<String> allCategoriesPageAddresses = (List<String>) getProperty(request.properties().get(SearchProperty.ALL_CATEGORIES_PAGE_ADDRESSES));
        for (String property : allCategoriesPageAddresses) {
            scrapRequest.setProperty(property);
            addresses.addAll(scraperService.scrap(scrapRequest));
        }
        return addresses;
    }

    public abstract List<?> search(final SearchRequest request) throws ValueForSearchPropertyException, ProductNotFoundException, ConnectionTimeoutException, IOException, ScraperIncorrectFieldException;
}
