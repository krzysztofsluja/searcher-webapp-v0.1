package org.sluja.searcher.webapp.service.scraper.search.implementation.product.category;

import lombok.RequiredArgsConstructor;
import org.openqa.selenium.WebElement;
import org.sluja.searcher.webapp.builder.request.search.shop.category.ShopCategoryPageSearchRequestBuilder;
import org.sluja.searcher.webapp.dto.product.request.search.category.ProductOneCategoryPageSearchRequest;
import org.sluja.searcher.webapp.dto.product.request.shop.category.ShopCategoryPageSearchRequest;
import org.sluja.searcher.webapp.dto.scraper.dynamic.DynamicWebsiteScrapRequest;
import org.sluja.searcher.webapp.dto.scraper.stat.StaticWebsiteScrapRequest;
import org.sluja.searcher.webapp.exception.product.general.ProductNotFoundException;
import org.sluja.searcher.webapp.service.factory.scraper.WebsiteScraperFactory;
import org.sluja.searcher.webapp.service.scraper.interfaces.WebsiteScraper;
import org.sluja.searcher.webapp.service.scraper.search.implementation.shop.category.ShopCategorySearchService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Qualifier("dynamicWebsiteProductCategoryPageSearchService")
@RequiredArgsConstructor
@Scope("prototype")
public class DynamicWebsiteProductCategoryPageSearchService extends ProductCategoryPageSearchService<DynamicWebsiteScrapRequest, ProductOneCategoryPageSearchRequest> {

    private final WebsiteScraperFactory websiteScraperFactory;
    private final ShopCategorySearchService<StaticWebsiteScrapRequest> staticWebsiteShopCategorySearchService;

    @Override
    public List<String> getAllCategoriesAddresses(final ProductOneCategoryPageSearchRequest request) throws ProductNotFoundException {
        final ShopCategoryPageSearchRequest searchRequest = ShopCategoryPageSearchRequestBuilder.buildStatic(request);
        return (List<String>) staticWebsiteShopCategorySearchService.searchList(searchRequest);
    }

    @Override
    public List<String> getCategoryPageAddresses(final ProductOneCategoryPageSearchRequest request) throws ProductNotFoundException {
        final List<String> allCategoriesPageAddresses = getAllCategoriesAddresses(request);
        return allCategoriesPageAddresses.stream()
                .filter(address -> doesPageContainProductCategoryName(address, request.getCategoryProperties()))
                .toList();
    }

    @Override
    public WebsiteScraper<List<WebElement>, DynamicWebsiteScrapRequest> getScraperService() {
        return (WebsiteScraper<List<WebElement>, DynamicWebsiteScrapRequest>) websiteScraperFactory.getScraper(true);
    }
}
