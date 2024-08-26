package org.sluja.searcher.webapp.dto.cart;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Builder
@Getter
public class UserCartProductsForShopDto {

    private String shopName;
    private List<UserCartProductDto> products;
    private Long productsQuantity;
    private BigDecimal productsPrice;
}
