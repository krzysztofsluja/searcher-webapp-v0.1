package org.sluja.searcher.webapp.builder.request.product.get;

import org.sluja.searcher.webapp.builder.request.product.ProductBuilder;
import org.sluja.searcher.webapp.dto.product.request.get.GetProductForShopNameAndCategoryRequest;
import org.sluja.searcher.webapp.dto.scraper.search.SearchRequest;
import org.sluja.searcher.webapp.enums.scraper.search.SearchProperty;
import org.sluja.searcher.webapp.exception.enums.search.ValueForSearchPropertyException;

import java.util.List;

public class GetProductForShopAndCategoryRequestBuilder extends ProductBuilder {

    public static GetProductForShopNameAndCategoryRequest build(final SearchRequest request, final String categoryName, final List<String> categoryProperties) throws ValueForSearchPropertyException {
        return new GetProductForShopNameAndCategoryRequest(request.isDynamicWebsite(),
                (String) getProperty(request, SearchProperty.HOME_PAGE_ADDRESS),
                (String) getProperty(request, SearchProperty.PAGE_ADDRESS_EXTRACT_ATTRIBUTE),
                (List<String>) getProperty(request, SearchProperty.ALL_CATEGORIES_PAGE_ADDRESSES),
                (String) getProperty(request, SearchProperty.CATEGORY_PAGE_AMOUNTS),
                categoryName,
                (String) getProperty(request, SearchProperty.SHOP_NAME),
                categoryProperties,
                (String) getProperty(request, SearchProperty.PRODUCT_INSTANCE),
                (String) getProperty(request, SearchProperty.CONTEXT),
                (String) getProperty(request, SearchProperty.PRODUCT_PRICE),
                (String) getProperty(request, SearchProperty.PRODUCT_NAME),
                (String) getProperty(request, SearchProperty.PRODUCT_DISCOUNT_PRICE),
                (String) getProperty(request, SearchProperty.PRODUCT_IMAGE_EXTRACT_ATTRIBUTE),
                (String) getProperty(request, SearchProperty.DIV),
                (String) getProperty(request, SearchProperty.PLAIN_PAGE_ADDRESS_TO_FORMAT),
                (List<String>) getProperty(request, SearchProperty.PRODUCT_PAGE_ADDRESSES),
                (List<String>) getProperty(request, SearchProperty.PRODUCT_IMAGE_ADDRESSES));
    }
}
