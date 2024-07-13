package org.sluja.searcher.webapp.dto.product.request.get;

import lombok.Getter;
import org.sluja.searcher.webapp.dto.product.request.GetProductRequest;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Getter
public class GetProductForShopNameRequest extends GetProductRequest implements Serializable {

    private String shopName;
    private Map<String, List<String>> categoriesProperties;
    private List<String> categories;
    public GetProductForShopNameRequest(final boolean dynamicWebsite,
                                        final String homePageAddress,
                                        final String pageAddressExtractAttribute,
                                        final List<String> allCategoriesPageAddresses,
                                        final String categoryPageAmounts,
                                        final String productInstance,
                                        final String context,
                                        final String productPrice,
                                        final String productName,
                                        final String productDiscountPrice,
                                        final String productImageExtractAttribute,
                                        final String div,
                                        final String plainPageAddressToFormat,
                                        final List<String> productPageAddresses,
                                        final List<String> productImageAddresses,
                                        final String shopName,
                                        final Map<String, List<String>> categoriesProperties,
                                        final List<String> categories) {
        super(dynamicWebsite, homePageAddress, pageAddressExtractAttribute, allCategoriesPageAddresses, categoryPageAmounts, productInstance, context, productPrice, productName, productDiscountPrice, productImageExtractAttribute, div, plainPageAddressToFormat, productPageAddresses, productImageAddresses);
        this.shopName = shopName;
        this.categoriesProperties = categoriesProperties;
        this.categories = categories;
    }

}
