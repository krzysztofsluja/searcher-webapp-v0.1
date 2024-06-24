package org.sluja.searcher.webapp.dto.product.request.search.category;

import lombok.Getter;
import org.sluja.searcher.webapp.dto.product.request.search.SearchServiceRequest;

import java.util.List;

@Getter
public class ProductCategoryPageSearchRequest extends SearchServiceRequest {

    private String pageAddressExtractAttribute;
    private List<String> allCategoriesPageAddresses;
    private String categoryPageAmounts;
    private List<String> categoryProperties;

    public ProductCategoryPageSearchRequest(final boolean dynamicWebsite, final String homePageAddress,
                                            final String pageAddressExtractAttribute,
                                            final List<String> allCategoriesPageAddresses,
                                            final String categoryPageAmounts,
                                            final List<String> categoryProperties) {
        super(dynamicWebsite, homePageAddress);
        this.pageAddressExtractAttribute = pageAddressExtractAttribute;
        this.allCategoriesPageAddresses = allCategoriesPageAddresses;
        this.categoryPageAmounts = categoryPageAmounts;
        this.categoryProperties = categoryProperties;
    }
}