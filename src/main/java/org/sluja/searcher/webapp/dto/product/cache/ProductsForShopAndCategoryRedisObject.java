package org.sluja.searcher.webapp.dto.product.cache;

import org.sluja.searcher.webapp.dto.product.ProductDTO;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

public record ProductsForShopAndCategoryRedisObject(String id,
                                                    String shopName,
                                                    String category,
                                                    LocalDate additionDate,
                                                    List<ProductDTO> products) implements Serializable {
}
