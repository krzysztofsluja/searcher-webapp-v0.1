package org.sluja.searcher.webapp.dto.creator.product;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.PastOrPresent;

import java.time.LocalDate;

public record ProductsForShopAndCategoryKeyCreatorData(@NotEmpty String shopName,
                                                       @NotEmpty String category,
                                                       @PastOrPresent LocalDate additionDate) {
}
