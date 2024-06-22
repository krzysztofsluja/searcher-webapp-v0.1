package org.sluja.searcher.webapp.service.factory.search;

import org.sluja.searcher.webapp.service.scraper.search.implementation.product.category.DynamicWebsiteProductCategoryPageSearchService;
import org.sluja.searcher.webapp.service.scraper.search.implementation.product.category.ProductCategoryPageSearchService;
import org.sluja.searcher.webapp.service.scraper.search.implementation.shop.category.ShopCategorySearchService;

public class ProductCategoryPageSearchFactory implements ISearchFactory<ProductCategoryPageSearchService<?,?>> {

    public static ProductCategoryPageSearchService<?,?> create(final boolean isDynamicWebsite) {
        return new ProductCategoryPageSearchFactory().createSearch(isDynamicWebsite);
    }
    @Override
    public ProductCategoryPageSearchService<?,?> createSearch(final boolean isDynamicWebsite) {
        return new DynamicWebsiteProductCategoryPageSearchService();
    }
}
