package org.sluja.searcher.webapp.dto.creator.product;

import jakarta.validation.constraints.NotEmpty;

import java.time.LocalDate;

public record ProductsForShopAndCategoryKeyCreatorData(@NotEmpty(message = "error.validation.shop.name.empty") String shopName,
                                                       @NotEmpty(message = "error.validation.category.name.empty") String category,
                                                       @NotEmpty(message = "error.validation.context.empty") String context,
                                                       LocalDate additionDate) {
}
