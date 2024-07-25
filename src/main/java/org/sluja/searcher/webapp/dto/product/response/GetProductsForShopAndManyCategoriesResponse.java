package org.sluja.searcher.webapp.dto.product.response;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.sluja.searcher.webapp.dto.product.ProductDTO;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Getter
@Builder
public class GetProductsForShopAndManyCategoriesResponse {

    @NotEmpty(message = "error.validation.shop.name.empty")
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
}
