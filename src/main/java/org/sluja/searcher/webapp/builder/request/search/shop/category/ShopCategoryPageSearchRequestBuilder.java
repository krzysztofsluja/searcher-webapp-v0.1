package org.sluja.searcher.webapp.builder.request.search.shop.category;

import org.sluja.searcher.webapp.dto.product.request.search.category.ProductOneCategoryPageSearchRequest;
import org.sluja.searcher.webapp.dto.product.request.shop.category.ShopCategoryPageSearchRequest;

public class ShopCategoryPageSearchRequestBuilder {

    public static ShopCategoryPageSearchRequest build(final boolean isDynamicWebsite, final ProductOneCategoryPageSearchRequest request) {
        return new ShopCategoryPageSearchRequest(isDynamicWebsite,
                request.getHomePageAddress(),
                request.getAllCategoriesPageAddresses(),
                request.getPageAddressExtractAttribute(),
                request.getShopName());
    }

    public static ShopCategoryPageSearchRequest buildStatic(final ProductOneCategoryPageSearchRequest request) {
        return build(false, request);
    }
}
