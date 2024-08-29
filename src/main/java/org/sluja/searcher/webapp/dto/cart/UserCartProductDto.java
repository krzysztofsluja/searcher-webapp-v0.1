package org.sluja.searcher.webapp.dto.cart;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.sluja.searcher.webapp.dto.product.ProductDTO;

@Getter
@AllArgsConstructor
public class UserCartProductDto {

    private String cartProductId;
    @Setter
    private int quantity;
    private ProductDTO product;
}
