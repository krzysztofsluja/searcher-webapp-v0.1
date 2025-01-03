package org.sluja.searcher.webapp.dto.product.request.get;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import org.sluja.searcher.webapp.dto.product.request.GetProductRequest;
import org.sluja.searcher.webapp.utils.dto.validation.DtoValidationErrorMessage;

import java.util.List;

@Getter
public class GetProductForShopNameAndCategoryRequest extends GetProductRequest {

    @NotEmpty(message = DtoValidationErrorMessage.CATEGORY_NAME_EMPTY)
    private String categoryName;
    @NotEmpty(message = DtoValidationErrorMessage.SHOP_NAME_EMPTY)
    private String shopName;
    private List<String> categoryProperties;

    public GetProductForShopNameAndCategoryRequest(final boolean dynamicWebsite,
                                                   final String homePageAddress,
                                                   final String pageAddressExtractAttribute,
                                                   final List<String> allCategoriesPageAddresses,
                                                   final String categoryPageAmounts,
                                                   final String categoryName,
                                                   final String shopName,
                                                   final List<String> categoryProperties,
                                                   final String productInstance,
                                                   final String context,
                                                   final String productPrice,
                                                   final String productName,
                                                   final String productDiscountPrice,
                                                   final String productImageExtractAttribute,
                                                   final String div,
                                                   final String plainPageAddressToFormat,
                                                   final List<String> productPageAddresses,
                                                   final List<String> productImageAddresses) {
        super(dynamicWebsite, homePageAddress, pageAddressExtractAttribute, allCategoriesPageAddresses, categoryPageAmounts, productInstance, context, productPrice, productName, productDiscountPrice, productImageExtractAttribute, div, plainPageAddressToFormat, productPageAddresses, productImageAddresses);
        this.categoryName = categoryName;
        this.shopName = shopName;
        this.categoryProperties = categoryProperties;
    }
}
