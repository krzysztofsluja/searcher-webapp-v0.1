package org.sluja.searcher.webapp.service.scraper.search.implementation.product.instance.dynamic;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Element;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.sluja.searcher.webapp.annotation.validation.InputValidation;
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
import org.sluja.searcher.webapp.exception.product.category.CategoryProductsOnOnePageException;
import org.sluja.searcher.webapp.exception.product.category.NoMoreCategoryPageAddressFoundException;
import org.sluja.searcher.webapp.exception.product.general.ProductNotFoundException;
import org.sluja.searcher.webapp.exception.product.instance.ProductInstanceNotFoundException;
import org.sluja.searcher.webapp.exception.scraper.ScraperIncorrectFieldException;
import org.sluja.searcher.webapp.service.factory.scraper.WebsiteScraperFactory;
import org.sluja.searcher.webapp.service.scraper.interfaces.WebsiteScraper;
import org.sluja.searcher.webapp.service.scraper.search.implementation.product.category.ProductCategoryPageSearchService;
import org.sluja.searcher.webapp.service.scraper.search.implementation.product.instance.ProductInstanceSearchService;
import org.sluja.searcher.webapp.utils.connector.IConnector;
import org.sluja.searcher.webapp.utils.logger.LoggerMessageUtils;
import org.sluja.searcher.webapp.utils.logger.LoggerUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
@Qualifier("dynamicWebsiteProductInstanceSearchService")
@RequiredArgsConstructor
@Slf4j
public class DynamicWebsiteProductInstanceSearchService extends ProductInstanceSearchService<DynamicWebsiteScrapRequest, ProductInstanceSearchRequest> {

    private final ProductCategoryPageSearchService<DynamicWebsiteScrapRequest, ProductOneCategoryPageSearchRequest> dynamicWebsiteProductCategoryPageSearchService;
    private final IConnector<WebDriver, DynamicWebsiteConnectRequest> dynamicWebsiteConnector;
    private final ProductInstanceSearchService<StaticWebsiteScrapRequest, ScrapProductInstanceSearchRequest> staticWebsiteProductInstanceSearchService;
    private final WebsiteScraperFactory websiteScraperFactory;
    private final LoggerMessageUtils loggerMessageUtils;
    @Override
    @InputValidation(inputs = {ProductInstanceSearchRequest.class})
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
                } catch (ConnectionTimeoutException | IOException | StaleElementReferenceException e) {
                    log.error(loggerMessageUtils.getErrorLogMessageWithDeclaredErrorMessage(LoggerUtils.getCurrentClassName(),
                            LoggerUtils.getCurrentMethodName(),
                            e.getMessage()));
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
            log.error(loggerMessageUtils.getErrorLogMessageWithDefaultErrorCode(LoggerUtils.getCurrentClassName(),
                    LoggerUtils.getCurrentMethodName(),
                    "error.validation.url.empty"));
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
            } catch (final ProductNotFoundException ex) {
                log.error(loggerMessageUtils.getErrorLogMessage(LoggerUtils.getCurrentClassName(),
                        LoggerUtils.getCurrentMethodName(),
                        ex.getMessageCode(),
                        ex.getErrorCode()));
                break;
            }
        }
        if(CollectionUtils.isEmpty(productInstances)) {
            throw new ProductInstanceNotFoundException();
        }
        return productInstances;
    }

    private void changePage(final ProductInstanceSearchRequest request, final WebDriver driver) throws ProductNotFoundException {
        //TODO logging
        if(StringUtils.isEmpty(request.getCategoryPageAmounts())) {
            throw new CategoryProductsOnOnePageException();
        }
        try {
            final DynamicWebsiteScrapRequest scrapRequest = new DynamicWebsiteScrapRequest(request.getCategoryPageAmounts(), driver);
            ((List<WebElement>) super.search(scrapRequest)).stream()
                    .distinct()
                    .findFirst()
                    .orElseThrow(() -> new NoMoreCategoryPageAddressFoundException(request.getShopName(), request.getCategoryName()))
                    .click();
            Thread.sleep(1500);
        } catch (ScraperIncorrectFieldException e) {
            log.error(loggerMessageUtils.getErrorLogMessage(LoggerUtils.getCurrentClassName(),
                    LoggerUtils.getCurrentMethodName(),
                    e.getMessageCode(),
                    e.getErrorCode()));
            throw new CategoryPageAddressNotFoundException();
        } catch (final InterruptedException ex) {
            log.error(loggerMessageUtils.getErrorLogMessageWithDeclaredErrorMessage(LoggerUtils.getCurrentClassName(),
                    LoggerUtils.getCurrentMethodName(),
                    ex.getMessage()));
            throw new CategoryPageAddressNotFoundException();
        }
    }

    @Override
    public WebsiteScraper getScraperService() {
        return websiteScraperFactory.getScraper(true);
    }
}
