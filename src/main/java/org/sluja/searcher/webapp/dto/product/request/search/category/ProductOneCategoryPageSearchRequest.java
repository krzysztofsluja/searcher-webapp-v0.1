package org.sluja.searcher.webapp.dto.product.request.search.category;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import org.sluja.searcher.webapp.utils.dto.validation.DtoValidationErrorMessage;

import java.util.List;

@Getter
public class ProductOneCategoryPageSearchRequest extends ProductCategoryPageSearchRequest{

    private List<String> categoryProperties;
    @NotEmpty(message = DtoValidationErrorMessage.SEARCH_REQUEST_ATTRIBUTE_EMPTY)
    private String productInstance;
    @NotEmpty(message = DtoValidationErrorMessage.SEARCH_REQUEST_ATTRIBUTE_EMPTY)
    private String shopName;

    public ProductOneCategoryPageSearchRequest(final boolean dynamicWebsite,
                                               final String homePageAddress,
                                               final String pageAddressExtractAttribute,
                                               final List<String> allCategoriesPageAddresses,
                                               final String categoryPageAmounts,
                                               final List<String> categoryProperties,
                                               final String productInstance,
                                               final String shopName) {
        super(dynamicWebsite, homePageAddress, pageAddressExtractAttribute, allCategoriesPageAddresses, categoryPageAmounts);
        this.categoryProperties = categoryProperties;
        this.productInstance = productInstance;
        this.shopName = shopName;
    }
}
