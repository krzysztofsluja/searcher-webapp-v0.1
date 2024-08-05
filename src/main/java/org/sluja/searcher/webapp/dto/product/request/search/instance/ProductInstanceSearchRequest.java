package org.sluja.searcher.webapp.dto.product.request.search.instance;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import org.sluja.searcher.webapp.dto.product.request.search.category.ProductCategoryPageSearchRequest;
import org.sluja.searcher.webapp.utils.dto.validation.DtoValidationErrorMessage;

import java.util.List;

@Getter
public class ProductInstanceSearchRequest extends ProductCategoryPageSearchRequest {

    @NotEmpty(message = DtoValidationErrorMessage.SEARCH_REQUEST_ATTRIBUTE_EMPTY)
    private String productInstance;
    private List<String> categoryProperties;
    @NotEmpty(message = DtoValidationErrorMessage.SEARCH_REQUEST_ATTRIBUTE_EMPTY)
    private String shopName;
    @NotEmpty(message = DtoValidationErrorMessage.SEARCH_REQUEST_ATTRIBUTE_EMPTY)
    private String  categoryName;

    public ProductInstanceSearchRequest(final boolean dynamicWebsite,
                                        final String homePageAddress,
                                        final String pageAddressExtractAttribute,
                                        final List<String> allCategoriesPageAddresses,
                                        final String categoryPageAmounts,
                                        final List<String> categoryProperties,
                                        final String productInstance,
                                        final String shopName,
                                        final String categoryName) {
        super(dynamicWebsite, homePageAddress, pageAddressExtractAttribute, allCategoriesPageAddresses, categoryPageAmounts);
        this.productInstance = productInstance;
        this.categoryProperties = categoryProperties;
        this.shopName = shopName;
        this.categoryName = categoryName;
    }

}
