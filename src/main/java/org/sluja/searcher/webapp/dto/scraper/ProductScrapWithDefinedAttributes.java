package org.sluja.searcher.webapp.dto.scraper;

import lombok.Builder;
import org.sluja.searcher.webapp.annotation.NullableOrEmptyField;

import java.io.Serializable;
import java.util.List;

import java.util.Map;

@Builder
public record ProductScrapWithDefinedAttributes(String shopName,
                                           String productPrice,
                                           String homePageAddress,
                                           @NullableOrEmptyField String productDescription,
                                           @NullableOrEmptyField String productDescriptionAttribute,
                                           String productName,
                                           @NullableOrEmptyField String productDiscountPrice,
                                           String productPageAddressAttribute,
                                           String productImageExtractAttribute,
                                           String div,
                                           String pageAddressExtractAttribute,
                                           @NullableOrEmptyField String plainPageAddressToFormat,
                                           String productInstance,
                                           List<String> productPageAddresses,
                                           List<String> productImageAddresses,
                                           String allCategoriesPageAddressElement,
                                           boolean dynamicWebsite,
                                           @NullableOrEmptyField String categoryPageAmounts,
                                           @NullableOrEmptyField List<String> allCategoriesPageAddresses,
                                           @NullableOrEmptyField List<String> allowedEmptyProperties,
                                           @NullableOrEmptyField List<String> categories,
                                           @NullableOrEmptyField Map<String, List<String>> categoryProperties,
                                           @NullableOrEmptyField Map<String, String> allCategoriesSubMenuVisibility,
                                           String context) implements Serializable {

/*    public static List<String> NO_IMAGE_ADDRESS() {
        return List.of(MessageReader.getMessage("attribute.product.image.address"));
    }*/
}
