package org.sluja.searcher.webapp.service.scraper.search.implementation.product.category;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.sluja.searcher.webapp.builder.request.connect.dynamic.DynamicWebsiteConnectRequestBuilder;
import org.sluja.searcher.webapp.dto.connect.DynamicWebsiteConnectRequest;
import org.sluja.searcher.webapp.dto.product.request.search.category.ProductOneCategoryPageSearchRequest;
import org.sluja.searcher.webapp.dto.product.request.shop.category.ShopCategoryPageSearchRequest;
import org.sluja.searcher.webapp.dto.scraper.dynamic.DynamicWebsiteScrapRequest;
import org.sluja.searcher.webapp.exception.connection.ConnectionTimeoutException;
import org.sluja.searcher.webapp.exception.enums.search.ValueForSearchPropertyException;
import org.sluja.searcher.webapp.exception.product.category.CategoryPageAddressNotFoundException;
import org.sluja.searcher.webapp.exception.product.general.ProductNotFoundException;
import org.sluja.searcher.webapp.exception.scraper.ScraperIncorrectFieldException;
import org.sluja.searcher.webapp.service.connector.dynamic.DynamicWebsiteConnector;
import org.sluja.searcher.webapp.service.factory.search.ShopCategorySearchFactory;
import org.sluja.searcher.webapp.service.scraper.search.implementation.shop.category.ShopCategorySearchService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Qualifier("dynamicWebsiteProductCategoryPageSearchService")
public class DynamicWebsiteProductCategoryPageSearchService extends ProductCategoryPageSearchService<DynamicWebsiteScrapRequest, ProductOneCategoryPageSearchRequest> {
    @Override
    public List<String> searchList(final ProductOneCategoryPageSearchRequest request) throws ProductNotFoundException {
        final List<String> categoryPageAddresses = getCategoryPageAddresses(request);
        if (CollectionUtils.isEmpty(categoryPageAddresses)) {
            throw new CategoryPageAddressNotFoundException();
        }
        if (!isCategoryPagePaginated(request)) {
            return categoryPageAddresses;
        }
        final List<String> productCategoryPageAddresses = new ArrayList<>();
        WebDriver driver = null;
        for (final String address : categoryPageAddresses) {
            final DynamicWebsiteConnectRequest connectRequest = DynamicWebsiteConnectRequestBuilder.build(address, null);
            try {
                driver = DynamicWebsiteConnector.INSTANCE.connectAndGetPage(connectRequest);
                productCategoryPageAddresses.addAll(getAllPageAddressesForGivenCategory(request, driver));
            } catch (ConnectionTimeoutException | IOException | ValueForSearchPropertyException e) {
                //TODO logging
                continue;
            }
        }
        if (Objects.nonNull(driver)) {
            DynamicWebsiteConnector.quit(driver);
        }
        return productCategoryPageAddresses;
    }

    private List<String> getAllCategoriesAddresses(final boolean isDynamicWebsite, final String homePageAddress, final List<String> allCategoriesPageAddresses, final String pageAddressExtractAttribute) throws ProductNotFoundException {
        final ShopCategorySearchService shopCategorySearchService = ShopCategorySearchFactory.create(false);
        final ShopCategoryPageSearchRequest searchRequest = new ShopCategoryPageSearchRequest(false, homePageAddress,allCategoriesPageAddresses, pageAddressExtractAttribute);
        return (List<String>) shopCategorySearchService.searchList(searchRequest);
    }

    private List<String> getCategoryPageAddresses(final ProductOneCategoryPageSearchRequest request) throws ProductNotFoundException {
        final List<String> allCategoriesPageAddresses = getAllCategoriesAddresses(request.isDynamicWebsite(), request.getHomePageAddress(), request.getAllCategoriesPageAddresses(), request.getPageAddressExtractAttribute());
        return allCategoriesPageAddresses.stream()
                .filter(address -> {
                    try {
                        return doesPageContainProductCategoryName(address, request.getCategoryProperties());
                    } catch (ValueForSearchPropertyException e) {
                        //TODO logging
                        return false;
                    }
                })
                .toList();
    }

    private boolean isCategoryPagePaginated(final ProductOneCategoryPageSearchRequest request) {
        return StringUtils.isNotEmpty(request.getCategoryPageAmounts());
    }

    private List<String> getAllPageAddressesForGivenCategory(final ProductOneCategoryPageSearchRequest request, final WebDriver driver) throws ValueForSearchPropertyException, CategoryPageAddressNotFoundException {
        final String currentCategoryUrl = driver.getCurrentUrl();
        if (StringUtils.isEmpty(currentCategoryUrl)) {
            throw new CategoryPageAddressNotFoundException();
        }
        final List<String> pageAddressesForCategory = new ArrayList<>();
        pageAddressesForCategory.add(currentCategoryUrl);
        while (true) {
            final DynamicWebsiteScrapRequest scrapRequest = new DynamicWebsiteScrapRequest(request.getCategoryPageAmounts(), driver);
            final String pageAddressExtractAttribute = request.getPageAddressExtractAttribute();
            final List<WebElement> pages;
            try {
                pages = (List<WebElement>) super.search(request, scrapRequest);
                final WebElement page = pages.stream()
                        .distinct()
                        .findFirst()
                        .orElseThrow(CategoryPageAddressNotFoundException::new);
                final String pageAddresses = page.getAttribute(pageAddressExtractAttribute);
                if (StringUtils.isNotEmpty(pageAddresses)) {
                    pageAddressesForCategory.add(pageAddresses);
                }
                page.click();
                Thread.sleep(1000);
            } catch (ValueForSearchPropertyException | ScraperIncorrectFieldException | InterruptedException ex) {
                //TODO logging
                continue;
            } catch (ProductNotFoundException ex1) {
                //TODO logging
                break;
            }
        }
        return pageAddressesForCategory;
    }

}
