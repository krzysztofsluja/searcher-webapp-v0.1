package org.sluja.searcher.webapp.dto.creator.product;

import jakarta.validation.constraints.NotEmpty;
import org.sluja.searcher.webapp.utils.dto.validation.DtoValidationErrorMessage;

import java.time.LocalDate;

public record ProductsForShopAndCategoryKeyCreatorData(@NotEmpty(message = DtoValidationErrorMessage.SHOP_NAME_EMPTY) String shopName,
                                                       @NotEmpty(message = DtoValidationErrorMessage.CATEGORY_NAME_EMPTY) String category,
                                                       @NotEmpty(message = DtoValidationErrorMessage.CONTEXT_EMPTY) String context,
                                                       LocalDate additionDate) {
}
