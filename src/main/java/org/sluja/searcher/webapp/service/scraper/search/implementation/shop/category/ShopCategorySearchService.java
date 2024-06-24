package org.sluja.searcher.webapp.service.scraper.search.implementation.shop.category;

import org.sluja.searcher.webapp.dto.product.request.shop.category.ShopCategoryPageSearchRequest;
import org.sluja.searcher.webapp.dto.scraper.ScrapRequest;
import org.sluja.searcher.webapp.exception.enums.search.ValueForSearchPropertyException;
import org.sluja.searcher.webapp.exception.product.general.ProductNotFoundException;
import org.sluja.searcher.webapp.exception.scraper.ScraperIncorrectFieldException;
import org.sluja.searcher.webapp.service.factory.scraper.WebsiteScraperFactory;
import org.sluja.searcher.webapp.service.interfaces.search.ISearch;
import org.sluja.searcher.webapp.service.scraper.interfaces.WebsiteScraper;
import org.sluja.searcher.webapp.service.scraper.search.BaseSearchService;

import java.util.ArrayList;
import java.util.List;

public abstract class ShopCategorySearchService<T extends ScrapRequest> extends BaseSearchService<ShopCategoryPageSearchRequest> implements ISearch<ShopCategoryPageSearchRequest, T> {

    @Override
    public List<?> search(final ShopCategoryPageSearchRequest request, T scrapRequest) throws ValueForSearchPropertyException, ProductNotFoundException, ScraperIncorrectFieldException {
        final WebsiteScraper scraperService = WebsiteScraperFactory.getScraper(request.isDynamicWebsite());
        final List<?> addresses = new ArrayList<>();
        final List<String> allCategoriesPageAddresses = request.getAllCategoriesPageAddresses();
        for (String property : allCategoriesPageAddresses) {
            scrapRequest.setProperty(property);
            addresses.addAll(scraperService.scrap(scrapRequest));
        }
        return addresses;
    }

}
