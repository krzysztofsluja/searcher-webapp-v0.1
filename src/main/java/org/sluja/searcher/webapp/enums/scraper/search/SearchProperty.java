package org.sluja.searcher.webapp.enums.scraper.search;

import lombok.Getter;
import org.atmosphere.config.service.Get;

@Getter
public enum SearchProperty {
    SHOP_NAME("shopName"),
    HOME_PAGE_ADDRESS("homePageAddress"),
    PRODUCT_DESCRIPTION("productDescription"),
    PRODUCT_DESCRIPTION_ATTRIBUTE("productDescriptionAttribute"),
    PRODUCT_NAME("productName"),
    PRODUCT_PAGE_ADDRESS_ATTRIBUTE("productPageAddressAttribute"),
    PRODUCT_IMAGE_EXTRACT_ATTRIBUTE("productImageExtractAttribute"),
    DIV("div"),
    PAGE_ADDRESS_EXTRACT_ATTRIBUTE("pageAddressExtractAttribute"),
    PLAIN_PAGE_ADDRESS_TO_FORMAT("plainPageAddressToFormat"),
    PRODUCT_INSTANCE("productInstance"),
    PRODUCT_PAGE_ADDRESSES("productPageAddresses"),
    PRODUCT_IMAGE_ADDRESSES("productImageAddresses"),
    ALL_CATEGORIES_PAGE_ADDRESS_ELEMENT("allCategoriesPageAddressElement"),
    CATEGORY_PAGE_AMOUNTS("categoryPageAmounts"),
    ALL_CATEGORIES_PAGE_ADDRESSES("allCategoriesPageAddresses"),
    ALLOWED_EMPTY_PROPERTIES("allowedEmptyProperties"),
    CATEGORIES("categories"),
    CATEGORY_PROPERTIES("categoryProperties"),
    ALL_CATEGORIES_SUB_MENU_VISIBILITY("allCategoriesSubMenuVisibility");

    private final String jsonValue;

    SearchProperty(String jsonValue) {
        this.jsonValue = jsonValue;
    }
}
