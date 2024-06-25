package org.sluja.searcher.webapp.service.factory.search;

import lombok.RequiredArgsConstructor;
import org.sluja.searcher.webapp.dto.product.request.search.category.ProductOneCategoryPageSearchRequest;
import org.sluja.searcher.webapp.dto.scraper.dynamic.DynamicWebsiteScrapRequest;
import org.sluja.searcher.webapp.service.scraper.search.implementation.product.category.ProductCategoryPageSearchService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Qualifier("productCategoryPageSearchFactory")
public class ProductCategoryPageSearchFactory implements ISearchFactory<ProductCategoryPageSearchService<?,?>> {

    private final ProductCategoryPageSearchService<DynamicWebsiteScrapRequest, ProductOneCategoryPageSearchRequest> dynamicWebsiteProductCategoryPageSearchService;
    @Override
    public ProductCategoryPageSearchService<?,?> createSearch(final boolean isDynamicWebsite) {
        return dynamicWebsiteProductCategoryPageSearchService;
    }
}
