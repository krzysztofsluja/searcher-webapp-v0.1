package org.sluja.searcher.webapp.service.scraper.search.implementation.product.instance.dynamic;

import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Element;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.sluja.searcher.webapp.builder.request.connect.dynamic.DynamicWebsiteConnectRequestBuilder;
import org.sluja.searcher.webapp.builder.request.product.category.ProductOneCategoryPageSearchRequestBuilder;
import org.sluja.searcher.webapp.builder.request.product.instance.ScrapProductInstanceSearchRequestBuilder;
import org.sluja.searcher.webapp.dto.connect.DynamicWebsiteConnectRequest;
import org.sluja.searcher.webapp.dto.product.request.search.category.ProductOneCategoryPageSearchRequest;
import org.sluja.searcher.webapp.dto.product.request.search.instance.ProductInstanceSearchRequest;
import org.sluja.searcher.webapp.dto.product.request.search.instance.ScrapProductInstanceSearchRequest;
import org.sluja.searcher.webapp.dto.scraper.dynamic.DynamicWebsiteScrapRequest;
import org.sluja.searcher.webapp.dto.scraper.stat.StaticWebsiteScrapRequest;
import org.sluja.searcher.webapp.exception.connection.ConnectionTimeoutException;
import org.sluja.searcher.webapp.exception.product.category.CategoryPageAddressNotFoundException;
import org.sluja.searcher.webapp.exception.product.general.ProductNotFoundException;
import org.sluja.searcher.webapp.exception.product.instance.ProductInstanceNotFoundException;
import org.sluja.searcher.webapp.exception.scraper.ScraperIncorrectFieldException;
import org.sluja.searcher.webapp.service.factory.scraper.WebsiteScraperFactory;
import org.sluja.searcher.webapp.service.scraper.interfaces.WebsiteScraper;
import org.sluja.searcher.webapp.service.scraper.search.implementation.product.category.ProductCategoryPageSearchService;
import org.sluja.searcher.webapp.service.scraper.search.implementation.product.instance.ProductInstanceSearchService;
import org.sluja.searcher.webapp.utils.connector.IConnector;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
@Qualifier("dynamicWebsiteProductInstanceSearchService")
@RequiredArgsConstructor
public class DynamicWebsiteProductInstanceSearchService extends ProductInstanceSearchService<DynamicWebsiteScrapRequest, ProductInstanceSearchRequest> {

    private final ProductCategoryPageSearchService<DynamicWebsiteScrapRequest, ProductOneCategoryPageSearchRequest> dynamicWebsiteProductCategoryPageSearchService;
    private final IConnector<WebDriver, DynamicWebsiteConnectRequest> dynamicWebsiteConnector;
    private final ProductInstanceSearchService<StaticWebsiteScrapRequest, ScrapProductInstanceSearchRequest> staticWebsiteProductInstanceSearchService;
    private final WebsiteScraperFactory websiteScraperFactory;
    @Override
    public List<Element> searchList(final ProductInstanceSearchRequest searchRequest) throws ProductNotFoundException {
        final ProductOneCategoryPageSearchRequest request = ProductOneCategoryPageSearchRequestBuilder.build(searchRequest);
        final List<String> categoryPageAddresses = (List<String>) dynamicWebsiteProductCategoryPageSearchService.getCategoryPageAddresses(request);
        if (CollectionUtils.isEmpty(categoryPageAddresses)) {
            throw new CategoryPageAddressNotFoundException();
        }
        final List<Element> productCategoryPageAddresses = new ArrayList<>();
        for (final String address : categoryPageAddresses) {
            final DynamicWebsiteConnectRequest connectRequest = DynamicWebsiteConnectRequestBuilder.build(address);
            int connectCounter = 0;
            while (connectCounter < 3) {
                try {
                    final WebDriver driver = dynamicWebsiteConnector.connectAndGetPage(connectRequest);
                    productCategoryPageAddresses.addAll(getProductInstanceFromAllPages(searchRequest, driver));
                    break;
                } catch (ConnectionTimeoutException | IOException e) {
                    //TODO logging
                    continue;
                } catch (StaleElementReferenceException ex) {
                    //TODO logging
                    connectCounter++;
                }
            }
        }
        //TODO quitting with aspects
        /*if (Objects.nonNull(driver)) {
            ((DynamicWebsiteConnector) dynamicWebsiteConnector).quit(driver);
        }*/
        return productCategoryPageAddresses;
    }

    private List<Element> getProductInstanceFromAllPages(final ProductInstanceSearchRequest request, final WebDriver driver) throws ProductNotFoundException {
        if (StringUtils.isEmpty(driver.getCurrentUrl())) {
            //TODO logging
            throw new CategoryPageAddressNotFoundException();
        }
        final List<Element> productInstances = new ArrayList<>();
        while (true) {
            try {
                final ScrapProductInstanceSearchRequest searchRequest = ScrapProductInstanceSearchRequestBuilder.build(request, driver.getPageSource());
                final List<Element> elements = (List<Element>) staticWebsiteProductInstanceSearchService.searchList(searchRequest);
                if (CollectionUtils.isNotEmpty(elements)) {
                    //TODO logging
                    productInstances.addAll(elements);
                }
                changePage(request, driver);
            } catch (ProductNotFoundException ex) {
                //TODO logging
                break;
            }
        }
        if(CollectionUtils.isEmpty(productInstances)) {
            //TODO logging
            throw new ProductInstanceNotFoundException();
        }
        return productInstances;
    }

    private void changePage(final ProductInstanceSearchRequest request, final WebDriver driver) throws ProductNotFoundException {
        if(StringUtils.isEmpty(request.getCategoryPageAmounts())) {
            //TODO logging
            throw new CategoryPageAddressNotFoundException();
        }
        try {
            final DynamicWebsiteScrapRequest scrapRequest = new DynamicWebsiteScrapRequest(request.getCategoryPageAmounts(), driver);
            ((List<WebElement>) super.search(scrapRequest)).stream()
                    .distinct()
                    .findFirst()
                    .orElseThrow(CategoryPageAddressNotFoundException::new)
                    .click();
            Thread.sleep(1500);
        } catch (ScraperIncorrectFieldException |
                 InterruptedException e) {
            //TODO logging
            throw new CategoryPageAddressNotFoundException();
        }
    }

    @Override
    public WebsiteScraper getScraperService() {
        return websiteScraperFactory.getScraper(true);
    }
}
