package org.sluja.searcher.webapp.builder.request.product.get;

import jakarta.validation.Valid;
import org.apache.commons.lang3.StringUtils;
import org.sluja.searcher.webapp.builder.request.product.ProductBuilder;
import org.sluja.searcher.webapp.dto.presentation.shop.attribute.ShopAttributeDto;
import org.sluja.searcher.webapp.dto.product.request.get.GetProductForShopNameRequest;
import org.sluja.searcher.webapp.dto.scraper.ProductScrapWithDefinedAttributes;
import org.sluja.searcher.webapp.dto.scraper.search.SearchRequest;
import org.sluja.searcher.webapp.enums.scraper.search.SearchProperty;
import org.sluja.searcher.webapp.exception.enums.search.ValueForSearchPropertyException;

import java.util.*;

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

    public static GetProductForShopNameRequest build(final List<ShopAttributeDto> shopAttributes, final boolean isDynamicWebsite, final Map<String, List<String>> categoryProperties, final List<String> categories) {
        return new GetProductForShopNameRequest(
                isDynamicWebsite,
                getPropertyValue(shopAttributes, SearchProperty.HOME_PAGE_ADDRESS).orElse(StringUtils.EMPTY),
                getPropertyValue(shopAttributes, SearchProperty.PAGE_ADDRESS_EXTRACT_ATTRIBUTE).orElse(StringUtils.EMPTY),
                getPropertyValueWhereTypeList(shopAttributes, SearchProperty.ALL_CATEGORIES_PAGE_ADDRESSES).orElse(Collections.emptyList()),
                getPropertyValue(shopAttributes, SearchProperty.CATEGORY_PAGE_AMOUNTS).orElse(StringUtils.EMPTY),
                getPropertyValue(shopAttributes, SearchProperty.PRODUCT_INSTANCE).orElse(StringUtils.EMPTY),
                getContextName(shopAttributes.getFirst()).orElse(StringUtils.EMPTY),
                getPropertyValue(shopAttributes, SearchProperty.PRODUCT_PRICE).orElse(StringUtils.EMPTY),
                getPropertyValue(shopAttributes, SearchProperty.PRODUCT_NAME).orElse(StringUtils.EMPTY),
                getPropertyValue(shopAttributes, SearchProperty.PRODUCT_DISCOUNT_PRICE).orElse(StringUtils.EMPTY),
                getPropertyValue(shopAttributes, SearchProperty.PRODUCT_IMAGE_EXTRACT_ATTRIBUTE).orElse(StringUtils.EMPTY),
                getPropertyValue(shopAttributes, SearchProperty.DIV).orElse(StringUtils.EMPTY),
                getPropertyValue(shopAttributes, SearchProperty.PLAIN_PAGE_ADDRESS_TO_FORMAT).orElse(StringUtils.EMPTY),
                getPropertyValueWhereTypeList(shopAttributes, SearchProperty.PRODUCT_PAGE_ADDRESSES).orElse(Collections.emptyList()),
                getPropertyValueWhereTypeList(shopAttributes, SearchProperty.PRODUCT_IMAGE_ADDRESSES).orElse(Collections.emptyList()),
                getPropertyValue(shopAttributes, SearchProperty.SHOP_NAME).orElse(StringUtils.EMPTY),
                categoryProperties,
                categories);
    }

    private static Optional<String> getPropertyValue(@Valid final List<ShopAttributeDto> attributeDtos, final SearchProperty property) {
        return attributeDtos.stream()
                .filter(attribute -> attribute.name().equalsIgnoreCase(property.getJsonValue()))
                .limit(1)
                .map(ShopAttributeDto::value)
                .findFirst();
    }

    private static Optional<String> getContextName(@Valid final ShopAttributeDto attribute) {
        return Optional.ofNullable(attribute.contextName());
    }

    private static Optional<List<String>> getPropertyValueWhereTypeList(@Valid final List<ShopAttributeDto> attributes, final SearchProperty property) {
        return Optional.of(attributes.stream()
                .filter(attribute -> attribute.name().equalsIgnoreCase(property.getJsonValue()))
                .filter(Objects::nonNull)
                .map(ShopAttributeDto::value)
                .toList());
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
