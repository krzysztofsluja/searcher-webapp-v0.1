package org.sluja.searcher.webapp.dto.product.request.search.category;

import lombok.Getter;

import java.util.List;

@Getter
public class ProductOneCategoryPageSearchRequest extends ProductCategoryPageSearchRequest{

    private List<String> categoryProperties;

    public ProductOneCategoryPageSearchRequest(final boolean dynamicWebsite,
                                               final String homePageAddress,
                                               final String pageAddressExtractAttribute,
                                               final List<String> allCategoriesPageAddresses,
                                               final String categoryPageAmounts,
                                               final List<String> categoryProperties) {
        super(dynamicWebsite, homePageAddress, pageAddressExtractAttribute, allCategoriesPageAddresses, categoryPageAmounts);
        this.categoryProperties = categoryProperties;
    }
}
