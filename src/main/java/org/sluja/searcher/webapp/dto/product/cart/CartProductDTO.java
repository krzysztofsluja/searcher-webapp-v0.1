package org.sluja.searcher.webapp.dto.product.cart;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Builder
@Getter
public class CartProductDTO {

    private final String id;
    private final String name;
    private final BigDecimal price;
    private final String shopName;
    private final String category;
    private final String context;
    private final String productPageAddress;
    private final String imageProductPageAddress;
    @Setter
    private Long quantity;
}
