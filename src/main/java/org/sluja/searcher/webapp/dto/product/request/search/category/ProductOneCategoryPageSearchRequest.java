package org.sluja.searcher.webapp.dto.product.request.search.category;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

import java.util.List;

@Getter
public class ProductOneCategoryPageSearchRequest extends ProductCategoryPageSearchRequest{

    private List<String> categoryProperties;
    @NotEmpty(message = "error.validation.search.request.attribute.empty")
    private String productInstance;

    public ProductOneCategoryPageSearchRequest(final boolean dynamicWebsite,
                                               final String homePageAddress,
                                               final String pageAddressExtractAttribute,
                                               final List<String> allCategoriesPageAddresses,
                                               final String categoryPageAmounts,
                                               final List<String> categoryProperties,
                                               final String productInstance) {
        super(dynamicWebsite, homePageAddress, pageAddressExtractAttribute, allCategoriesPageAddresses, categoryPageAmounts);
        this.categoryProperties = categoryProperties;
        this.productInstance = productInstance;
    }
}
