package org.sluja.searcher.webapp.dto.product.request.search.instance;

import lombok.Getter;
import org.sluja.searcher.webapp.dto.product.request.search.category.ProductCategoryPageSearchRequest;

import java.util.List;

@Getter
public class ProductInstanceSearchRequest extends ProductCategoryPageSearchRequest {

    private String productInstance;
    private List<String> categoryProperties;
    public ProductInstanceSearchRequest(final boolean dynamicWebsite,
                                        final String homePageAddress,
                                        final String pageAddressExtractAttribute,
                                        final List<String> allCategoriesPageAddresses,
                                        final String categoryPageAmounts,
                                        final List<String> categoryProperties,
                                        final String productInstance) {
        super(dynamicWebsite, homePageAddress, pageAddressExtractAttribute, allCategoriesPageAddresses, categoryPageAmounts);
        this.productInstance = productInstance;
        this.categoryProperties = categoryProperties;
    }

}