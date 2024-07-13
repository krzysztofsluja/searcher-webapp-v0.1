package org.sluja.searcher.webapp.service.scraper.search.implementation.shop.category;

import org.apache.commons.collections4.CollectionUtils;
import org.sluja.searcher.webapp.dto.product.request.shop.category.ShopCategoryPageSearchRequest;
import org.sluja.searcher.webapp.dto.scraper.ScrapRequest;
import org.sluja.searcher.webapp.exception.product.category.CategoryPageAddressNotFoundException;
import org.sluja.searcher.webapp.exception.product.general.ProductNotFoundException;
import org.sluja.searcher.webapp.exception.scraper.ScraperIncorrectFieldException;
import org.sluja.searcher.webapp.service.interfaces.search.ISearch;
import org.sluja.searcher.webapp.service.scraper.search.BaseSearchService;

import java.util.ArrayList;
import java.util.List;

public abstract class ShopCategorySearchService<T extends ScrapRequest> extends BaseSearchService<ShopCategoryPageSearchRequest> implements ISearch<ShopCategoryPageSearchRequest, T> {
    @Override
    public List<?> search(final ShopCategoryPageSearchRequest request, T scrapRequest) throws ProductNotFoundException {
        final List<String> addresses = new ArrayList<>();
        final List<String> categoriesPageAddresses = request.getAllCategoriesPageAddresses();
        for (final String property : categoriesPageAddresses) {
            scrapRequest.setProperty(property);
            try {
                final List<String> pageAddresses = (List<String>) getScraperService().scrap(scrapRequest);
                addresses.addAll(pageAddresses);
            } catch (ScraperIncorrectFieldException ex) {
                //TODO logging
                if(!(categoriesPageAddresses.indexOf(property) == (categoriesPageAddresses.size() - 1))) {
                    //TODO logging
                    continue;
                }
                throw new CategoryPageAddressNotFoundException();
            }
        }
        if(CollectionUtils.isEmpty(addresses)) {
            throw new CategoryPageAddressNotFoundException();
        }
        return addresses;
    }

}
