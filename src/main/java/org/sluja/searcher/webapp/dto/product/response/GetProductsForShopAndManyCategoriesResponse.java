package org.sluja.searcher.webapp.dto.product.response;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;
import org.sluja.searcher.webapp.dto.product.ProductDTO;
import org.sluja.searcher.webapp.utils.dto.validation.DtoValidationErrorMessage;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Getter
@Builder
@ToString
public class GetProductsForShopAndManyCategoriesResponse {

    @NotEmpty(message = DtoValidationErrorMessage.SHOP_NAME_EMPTY)
    private String shopName;
    private List<String> categories;
    private Map<String, List<ProductDTO>> productsForCategory;

    public static GetProductsForShopAndManyCategoriesResponse empty() {
        return GetProductsForShopAndManyCategoriesResponse.builder()
                .shopName(StringUtils.EMPTY)
                .categories(Collections.emptyList())
                .productsForCategory(Collections.emptyMap())
                .build();
    }

    public static GetProductsForShopAndManyCategoriesResponse empty(final String shopName, final List<String> categories) {
        return GetProductsForShopAndManyCategoriesResponse.builder()
                .shopName(shopName)
                .categories(categories)
                .productsForCategory(Collections.emptyMap())
                .build();
    }
}
