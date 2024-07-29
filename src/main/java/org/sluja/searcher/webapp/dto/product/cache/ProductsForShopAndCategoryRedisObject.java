package org.sluja.searcher.webapp.dto.product.cache;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import org.sluja.searcher.webapp.dto.product.ProductDTO;
import org.sluja.searcher.webapp.utils.dto.validation.DtoValidationErrorMessage;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

public record ProductsForShopAndCategoryRedisObject(@Pattern(regexp = "^(?!-)([a-zA-Z0-9]+-)*\\d{4}-\\d{1,2}-\\d{1,2}$", message = DtoValidationErrorMessage.CACHE_PRODUCT_ID_INCORRECT) String id,
                                                    @NotEmpty(message = DtoValidationErrorMessage.SHOP_NAME_EMPTY) String shopName,
                                                    @NotEmpty(message = DtoValidationErrorMessage.CATEGORY_NAME_EMPTY) String category,
                                                    LocalDate additionDate,
                                                    List<ProductDTO> products) implements Serializable {
}
