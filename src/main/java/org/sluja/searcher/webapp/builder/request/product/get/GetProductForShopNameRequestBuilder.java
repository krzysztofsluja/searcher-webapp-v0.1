package org.sluja.searcher.webapp.builder.request.product.get;

import org.sluja.searcher.webapp.builder.request.product.ProductBuilder;
import org.sluja.searcher.webapp.dto.product.request.get.GetProductForShopNameRequest;
import org.sluja.searcher.webapp.dto.scraper.ProductScrapWithDefinedAttributes;
import org.sluja.searcher.webapp.dto.scraper.search.SearchRequest;
import org.sluja.searcher.webapp.enums.scraper.search.SearchProperty;
import org.sluja.searcher.webapp.exception.enums.search.ValueForSearchPropertyException;

import java.util.List;
import java.util.Map;

public class GetProductForShopNameRequestBuilder extends ProductBuilder {

    public static GetProductForShopNameRequest build(final SearchRequest request, final String shopName) throws ValueForSearchPropertyException {
        return new GetProductForShopNameRequest(
                request.isDynamicWebsite(),
                (String) getProperty(request, SearchProperty.HOME_PAGE_ADDRESS),
                (String) getProperty(request, SearchProperty.PAGE_ADDRESS_EXTRACT_ATTRIBUTE),
                (List<String>) getProperty(request, SearchProperty.ALL_CATEGORIES_PAGE_ADDRESSES),
                (String) getProperty(request, SearchProperty.CATEGORY_PAGE_AMOUNTS),
                (String) getProperty(request, SearchProperty.PRODUCT_INSTANCE),
                (String) getProperty(request, SearchProperty.CONTEXT),
                (String) getProperty(request, SearchProperty.PRODUCT_PRICE),
                (String) getProperty(request, SearchProperty.PRODUCT_NAME),
                (String) getProperty(request, SearchProperty.PRODUCT_DISCOUNT_PRICE),
                (String) getProperty(request, SearchProperty.PRODUCT_IMAGE_EXTRACT_ATTRIBUTE),
                (String) getProperty(request, SearchProperty.DIV),
                (String) getProperty(request, SearchProperty.PLAIN_PAGE_ADDRESS_TO_FORMAT),
                (List<String>) getProperty(request, SearchProperty.PRODUCT_PAGE_ADDRESSES),
                (List<String>) getProperty(request, SearchProperty.PRODUCT_IMAGE_ADDRESSES),
                shopName,
                (Map<String, List<String>>) getProperty(request, SearchProperty.CATEGORY_PROPERTIES),
                (List<String>) getProperty(request, SearchProperty.CATEGORIES));
    }

    public static GetProductForShopNameRequest build(final ProductScrapWithDefinedAttributes request) {
        return new GetProductForShopNameRequest(
                request.dynamicWebsite(),
                request.homePageAddress(),
                request.pageAddressExtractAttribute(),
                request.allCategoriesPageAddresses(),
                request.categoryPageAmounts(),
                request.productInstance(),
                request.context(),
                request.productPrice(),
                request.productName(),
                request.productDiscountPrice(),
                request.productImageExtractAttribute(),
                request.div(),
                request.plainPageAddressToFormat(),
                request.productPageAddresses(),
                request.productImageAddresses(),
                request.shopName(),
                request.categoryProperties(),
                request.categories()
        );
    }
}
