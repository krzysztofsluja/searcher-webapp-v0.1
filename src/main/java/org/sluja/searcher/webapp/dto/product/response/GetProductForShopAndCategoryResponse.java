package org.sluja.searcher.webapp.dto.product.response;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import org.sluja.searcher.webapp.dto.product.ProductDTO;

import java.io.Serializable;
import java.util.List;

@Builder
public record GetProductForShopAndCategoryResponse(@NotEmpty(message = "error.validation.shop.name.empty") String shopName,
                                                   @NotEmpty(message = "error.validation.category.name.empty") String categoryName,
                                                   @NotEmpty(message = "error.validation.context.name.empty") String context,
                                                  List<ProductDTO> products) implements Serializable {
}
