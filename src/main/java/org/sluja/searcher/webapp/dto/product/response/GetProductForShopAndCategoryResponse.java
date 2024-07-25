package org.sluja.searcher.webapp.dto.product.response;

import lombok.Builder;
import org.sluja.searcher.webapp.dto.product.ProductDTO;

import java.io.Serializable;
import java.util.List;

@Builder
public record GetProductForShopAndCategoryResponse(String shopName,
                                                  String categoryName,
                                                  String context,
                                                  List<ProductDTO> products) implements Serializable {
}
