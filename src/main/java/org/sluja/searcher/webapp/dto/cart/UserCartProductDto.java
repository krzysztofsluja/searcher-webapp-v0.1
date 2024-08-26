package org.sluja.searcher.webapp.dto.cart;

import lombok.Getter;
import org.sluja.searcher.webapp.dto.product.ProductDTO;

@Getter
public class UserCartProductDto {

    private String cartProductId;
    private int quantity;
    private ProductDTO product;
}
