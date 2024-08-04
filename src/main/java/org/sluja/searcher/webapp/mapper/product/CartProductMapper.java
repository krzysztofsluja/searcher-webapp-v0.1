package org.sluja.searcher.webapp.mapper.product;

import org.sluja.searcher.webapp.dto.product.ProductDTO;
import org.sluja.searcher.webapp.dto.product.cart.CartProductDTO;

public class CartProductMapper {

    public static CartProductDTO map(final ProductDTO product) {
        return CartProductDTO.builder()
                .id(product.id())
                .name(product.name())
                .price(product.price())
                .shopName(product.shopName())
                .category(product.category())
                .context(product.context())
                .productPageAddress(product.productPageAddress().getFirst())
                .imageProductPageAddress(product.imageProductPageAddress().getFirst())
                .quantity(1L)
                .build();
    }
}
