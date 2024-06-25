package org.sluja.searcher.webapp.dto.product.request.search.category;

import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
public class ProductManyCategoriesPageSearchRequest extends ProductCategoryPageSearchRequest {

    private List<String> categories;
    private Map<String, List<String>> categoryProperties;
    public ProductManyCategoriesPageSearchRequest(final boolean dynamicWebsite,
                                                  final String homePageAddress,
                                                  final String pageAddressExtractAttribute,
                                                  final List<String> allCategoriesPageAddresses,
                                                  final String categoryPageAmounts,
                                                  final Map<String, List<String>> categoryProperties,
                                                  final List<String> categories) {
        super(dynamicWebsite, homePageAddress, pageAddressExtractAttribute, allCategoriesPageAddresses, categoryPageAmounts);
        this.categories = categories;
        this.categoryProperties = categoryProperties;
    }
}
