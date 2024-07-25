package org.sluja.searcher.webapp.dto.product.request.search.category;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import org.sluja.searcher.webapp.dto.product.request.search.SearchServiceRequest;

import java.util.List;

@Getter
public class ProductCategoryPageSearchRequest extends SearchServiceRequest {

    @NotEmpty(message = "error.validation.search.request.attribute.empty")
    private String pageAddressExtractAttribute;
    private List<String> allCategoriesPageAddresses;
    private String categoryPageAmounts;

    public ProductCategoryPageSearchRequest(final boolean dynamicWebsite,
                                            final String homePageAddress,
                                            final String pageAddressExtractAttribute,
                                            final List<String> allCategoriesPageAddresses,
                                            final String categoryPageAmounts) {
        super(dynamicWebsite, homePageAddress);
        this.pageAddressExtractAttribute = pageAddressExtractAttribute;
        this.allCategoriesPageAddresses = allCategoriesPageAddresses;
        this.categoryPageAmounts = categoryPageAmounts;
    }
}
