package org.sluja.searcher.webapp.mapper.product;

import org.sluja.searcher.webapp.dto.cart.UserCartProductDto;
import org.sluja.searcher.webapp.dto.product.ProductDTO;

public class CartProductMapper {

    public static UserCartProductDto map(final ProductDTO product) {
        return new UserCartProductDto(product.id(), 1, product);
    }
}
