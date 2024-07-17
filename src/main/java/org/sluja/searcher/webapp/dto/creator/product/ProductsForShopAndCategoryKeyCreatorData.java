package org.sluja.searcher.webapp.dto.creator.product;

import java.time.LocalDate;

public record ProductsForShopAndCategoryKeyCreatorData(String shopName,
                                                       String category,
                                                       LocalDate additionDate) {
}
