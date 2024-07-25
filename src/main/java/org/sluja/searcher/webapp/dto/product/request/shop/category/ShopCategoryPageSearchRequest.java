package org.sluja.searcher.webapp.dto.product.request.shop.category;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import org.sluja.searcher.webapp.dto.product.request.search.SearchServiceRequest;

import java.util.List;

@Getter
public class ShopCategoryPageSearchRequest extends SearchServiceRequest {

    private List<String> allCategoriesPageAddresses;
    @NotEmpty(message = "error.validation.search.request.attribute.empty")
    private String pageAddressExtractAttribute;

    public ShopCategoryPageSearchRequest(final boolean dynamicWebsite,
                                         final String homePageAddress,
                                         final List<String> allCategoriesPageAddresses,
                                         final String pageAddressExtractAttribute) {
        super(dynamicWebsite, homePageAddress);
        this.allCategoriesPageAddresses = allCategoriesPageAddresses;
        this.pageAddressExtractAttribute = pageAddressExtractAttribute;
    }
}
