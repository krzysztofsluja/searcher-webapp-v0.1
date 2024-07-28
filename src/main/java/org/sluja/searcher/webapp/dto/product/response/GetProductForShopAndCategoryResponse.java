package org.sluja.searcher.webapp.dto.product.response;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import org.sluja.searcher.webapp.dto.product.ProductDTO;
import org.sluja.searcher.webapp.utils.dto.validation.DtoValidationErrorMessage;

import java.io.Serializable;
import java.util.List;

@Builder
public record GetProductForShopAndCategoryResponse(@NotEmpty(message = DtoValidationErrorMessage.SHOP_NAME_EMPTY) String shopName,
                                                   @NotEmpty(message = DtoValidationErrorMessage.CATEGORY_NAME_EMPTY) String categoryName,
                                                   @NotEmpty(message = DtoValidationErrorMessage.CONTEXT_EMPTY) String context,
                                                  List<ProductDTO> products) implements Serializable {
}
