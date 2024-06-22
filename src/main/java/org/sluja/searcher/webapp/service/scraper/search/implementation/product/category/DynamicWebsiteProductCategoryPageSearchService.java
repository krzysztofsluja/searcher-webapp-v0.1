package org.sluja.searcher.webapp.service.scraper.search.implementation.product.category;

import org.apache.commons.collections4.CollectionUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.sluja.searcher.webapp.builder.request.search.SearchRequestBuilder;
import org.sluja.searcher.webapp.dto.connect.DynamicWebsiteConnectRequest;
import org.sluja.searcher.webapp.dto.scraper.dynamic.DynamicWebsiteScrapRequest;
import org.sluja.searcher.webapp.dto.scraper.search.DynamicWebsiteSearchRequest;
import org.sluja.searcher.webapp.dto.scraper.search.SearchRequest;
import org.sluja.searcher.webapp.enums.scraper.search.SearchProperty;
import org.sluja.searcher.webapp.exception.connection.ConnectionTimeoutException;
import org.sluja.searcher.webapp.exception.enums.search.ValueForSearchPropertyException;
import org.sluja.searcher.webapp.exception.product.general.ProductNotFoundException;
import org.sluja.searcher.webapp.exception.product.category.CategoryPageAddressNotFoundException;
import org.sluja.searcher.webapp.exception.product.shop.ShopCategoriesPageAddressesNotFoundException;
import org.sluja.searcher.webapp.exception.scraper.ScraperIncorrectFieldException;
import org.sluja.searcher.webapp.exception.scraper.search.IncorrectInputException;
import org.sluja.searcher.webapp.service.connector.dynamic.DynamicWebsiteConnector;
import org.sluja.searcher.webapp.service.factory.search.ShopCategorySearchFactory;
import org.sluja.searcher.webapp.service.scraper.search.implementation.shop.category.ShopCategorySearchService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DynamicWebsiteProductCategoryPageSearchService extends ProductCategoryPageSearchService<DynamicWebsiteScrapRequest, DynamicWebsiteSearchRequest> {
    @Override
    public List<String> search(final DynamicWebsiteSearchRequest request) throws ProductNotFoundException {
        final List<String> categoryPageAddresses = getCategoryPageAddresses(request);
        if (CollectionUtils.isEmpty(categoryPageAddresses)) {
            throw new CategoryPageAddressNotFoundException();
        }
        if (!isCategoryPagePaginated(request)) {
            return categoryPageAddresses;
        }
        final List<String> productCategoryPageAddresses = new ArrayList<>();

        for (final String address : categoryPageAddresses) {
            productCategoryPageAddresses.add(address);
            final DynamicWebsiteConnectRequest connectRequest = DynamicWebsiteConnectRequest.builder()
                    .url(address)
                    .driver(null)
                    .build();
            final WebDriver driver;
            try {
                driver = DynamicWebsiteConnector.INSTANCE.connectAndGetPage(connectRequest);
            } catch (ConnectionTimeoutException | IOException e) {
                //TODO logging
             continue;
            }
            while(true) {
                final DynamicWebsiteScrapRequest scrapRequest = new DynamicWebsiteScrapRequest(((List<String>) request.getProperties().get(SearchProperty.CATEGORY_PAGE_AMOUNTS)).get(0), driver);
                final String pageAddressExtractAttribute = (String) request.getProperties().get(SearchProperty.PAGE_ADDRESS_EXTRACT_ATTRIBUTE);
                final List<WebElement> pages;
                try {
                    pages = (List<WebElement>) super.search(request, scrapRequest);
                    final WebElement page = pages.getFirst();
                    final String pageAddresses = pages.stream()
                            .map(element -> element.getAttribute(pageAddressExtractAttribute))
                            .distinct()
                            .findFirst()
                            .orElseThrow(ValueForSearchPropertyException::new);
                    if(page != null) {
                        page.click();
                        Thread.sleep(1000);
                    } else {
                        break;
                    }
                } catch (ValueForSearchPropertyException | ScraperIncorrectFieldException | InterruptedException e) {
                    //TODO logging
                    continue;
                }
            }

/*            for (final WebElement page : pages) {

                productCategoryPageAddresses.add(pageAddress);
                try {
                    if (super.doesPageHaveNextInOrder(page.getText())) {
                        page.click();
                    }
                } catch (IncorrectInputException e) {
                    //TODO logging
                    continue;
                }
            }*/
        }
        return productCategoryPageAddresses;
    }

    private List<String> getAllCategoriesAddresses(final DynamicWebsiteSearchRequest request) throws ProductNotFoundException {
        final ShopCategorySearchService shopCategorySearchService = ShopCategorySearchFactory.create(false);
        final List<SearchProperty> searchProperties = List.of(SearchProperty.HOME_PAGE_ADDRESS, SearchProperty.PAGE_ADDRESS_EXTRACT_ATTRIBUTE, SearchProperty.ALL_CATEGORIES_PAGE_ADDRESSES);
        try {
            final SearchRequest searchRequest = SearchRequestBuilder.buildFromAnotherRequest(false, searchProperties, request);
            return (List<String>) shopCategorySearchService.search(searchRequest);
        } catch (IncorrectInputException ex) {
            //TODO logging
            throw new ShopCategoriesPageAddressesNotFoundException();
        }
    }

    private List<String> getCategoryPageAddresses(final DynamicWebsiteSearchRequest request) throws ProductNotFoundException {
        final List<String> allCategoriesPageAddresses = getAllCategoriesAddresses(request);
        return allCategoriesPageAddresses.stream()
                .filter(address -> {
                    try {
                        return doesPageContainProductCategoryName(address, request);
                    } catch (ValueForSearchPropertyException e) {
                        //TODO logging
                        return false;
                    }
                })
                .toList();
    }

    private boolean isCategoryPagePaginated(final DynamicWebsiteSearchRequest request) {
        try {
            return CollectionUtils.isNotEmpty(((List<String>) getProperty(request.getProperties().get(SearchProperty.CATEGORY_PAGE_AMOUNTS))));
        } catch (ValueForSearchPropertyException e) {
            //TODO logging
            return false;
        }
    }

}
