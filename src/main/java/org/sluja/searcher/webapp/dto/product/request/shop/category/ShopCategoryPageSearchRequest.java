package org.sluja.searcher.webapp.dto.product.request.shop.category;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import org.sluja.searcher.webapp.dto.product.request.search.SearchServiceRequest;
import org.sluja.searcher.webapp.utils.dto.validation.DtoValidationErrorMessage;

import java.util.List;

@Getter
public class ShopCategoryPageSearchRequest extends SearchServiceRequest {

    private List<String> allCategoriesPageAddresses;
    @NotEmpty(message = DtoValidationErrorMessage.SEARCH_REQUEST_ATTRIBUTE_EMPTY)
    private String pageAddressExtractAttribute;
    @NotEmpty(message = DtoValidationErrorMessage.SHOP_NAME_EMPTY)
    private String shopName;

    public ShopCategoryPageSearchRequest(final boolean dynamicWebsite,
                                         final String homePageAddress,
                                         final List<String> allCategoriesPageAddresses,
                                         final String pageAddressExtractAttribute,
                                         final String shopName) {
        super(dynamicWebsite, homePageAddress);
        this.allCategoriesPageAddresses = allCategoriesPageAddresses;
        this.pageAddressExtractAttribute = pageAddressExtractAttribute;
        this.shopName = shopName;
    }
}
