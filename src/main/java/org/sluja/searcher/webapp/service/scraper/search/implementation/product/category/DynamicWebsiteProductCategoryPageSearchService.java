package org.sluja.searcher.webapp.service.scraper.search.implementation.product.category;

import lombok.RequiredArgsConstructor;
import org.openqa.selenium.WebDriver;
import org.sluja.searcher.webapp.dto.connect.DynamicWebsiteConnectRequest;
import org.sluja.searcher.webapp.dto.product.request.search.category.ProductOneCategoryPageSearchRequest;
import org.sluja.searcher.webapp.dto.product.request.shop.category.ShopCategoryPageSearchRequest;
import org.sluja.searcher.webapp.dto.scraper.dynamic.DynamicWebsiteScrapRequest;
import org.sluja.searcher.webapp.dto.scraper.stat.StaticWebsiteScrapRequest;
import org.sluja.searcher.webapp.exception.enums.search.ValueForSearchPropertyException;
import org.sluja.searcher.webapp.exception.product.general.ProductNotFoundException;
import org.sluja.searcher.webapp.service.factory.search.ShopCategorySearchFactory;
import org.sluja.searcher.webapp.service.scraper.search.implementation.shop.category.ShopCategorySearchService;
import org.sluja.searcher.webapp.utils.connector.IConnector;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Qualifier("dynamicWebsiteProductCategoryPageSearchService")
@RequiredArgsConstructor
@Scope("prototype")
public class DynamicWebsiteProductCategoryPageSearchService extends ProductCategoryPageSearchService<DynamicWebsiteScrapRequest, ProductOneCategoryPageSearchRequest> {

    private final IConnector<WebDriver, DynamicWebsiteConnectRequest> dynamicWebsiteConnector;
    //private final ProductInstanceSearchService<StaticWebsiteScrapRequest, ProductInstanceSearchRequest> staticWebsiteProductInstanceSearchService;
    private final ShopCategorySearchService<StaticWebsiteScrapRequest> staticWebsiteShopCategorySearchService;

    @Override
    public List<String> getAllCategoriesAddresses(final ProductOneCategoryPageSearchRequest request) throws ProductNotFoundException {
        final ShopCategorySearchService shopCategorySearchService = ShopCategorySearchFactory.create(false);
        final ShopCategoryPageSearchRequest searchRequest = new ShopCategoryPageSearchRequest(false, request.getHomePageAddress(), request.getAllCategoriesPageAddresses(), request.getPageAddressExtractAttribute());
        return (List<String>) shopCategorySearchService.searchList(searchRequest);
    }

    @Override
    public List<String> getCategoryPageAddresses(final ProductOneCategoryPageSearchRequest request) throws ProductNotFoundException {
        final List<String> allCategoriesPageAddresses = getAllCategoriesAddresses(request);
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

}
