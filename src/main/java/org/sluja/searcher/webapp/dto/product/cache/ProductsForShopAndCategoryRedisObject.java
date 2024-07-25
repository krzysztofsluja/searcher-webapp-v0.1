package org.sluja.searcher.webapp.dto.product.cache;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import org.sluja.searcher.webapp.dto.product.ProductDTO;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

public record ProductsForShopAndCategoryRedisObject(@Pattern(regexp = "^(?!-)(.*-)+(\\\\d{4}-\\\\d{1,2}-([12][0-9]|3[01]|0?[1-9]))$", message = "error.validation.cache.id.incorrect") String id,
                                                    @NotEmpty(message = "error.validation.shop.name.empty" ) String shopName,
                                                    @NotEmpty(message = "error.validation.category .name.empty") String category,
                                                    LocalDate additionDate,
                                                    List<ProductDTO> products) implements Serializable {
}
