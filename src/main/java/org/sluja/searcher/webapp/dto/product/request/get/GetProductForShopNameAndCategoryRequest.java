package org.sluja.searcher.webapp.dto.product.request.get;

import lombok.Getter;
import org.sluja.searcher.webapp.dto.product.request.search.category.ProductCategoryPageSearchRequest;

import java.util.List;

@Getter
public class GetProductForShopNameAndCategoryRequest extends ProductCategoryPageSearchRequest {

    private String categoryName;
    private String shopName;
    private String productInstance;
    private List<String> categoryProperties;
    private String context;
    private String productPrice;
    private String productName;
    private String productDiscountPrice;
    private String productImageExtractAttribute;
    private String div;
    private String plainPageAddressToFormat;
    private List<String> productPageAddresses;
    private List<String> productImageAddresses;

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
        super(dynamicWebsite, homePageAddress, pageAddressExtractAttribute, allCategoriesPageAddresses, categoryPageAmounts);
        this.categoryName = categoryName;
        this.shopName = shopName;
        this.productInstance = productInstance;
        this.categoryProperties = categoryProperties;
        this.context = context;
        this.productPrice = productPrice;
        this.productName = productName;
        this.productDiscountPrice = productDiscountPrice;
        this.productImageExtractAttribute = productImageExtractAttribute;
        this.div = div;
        this.plainPageAddressToFormat = plainPageAddressToFormat;
        this.productPageAddresses = productPageAddresses;
        this.productImageAddresses = productImageAddresses;
    }
}
