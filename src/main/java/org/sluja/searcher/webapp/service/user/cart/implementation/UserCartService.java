package org.sluja.searcher.webapp.service.user.cart.implementation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.sluja.searcher.webapp.annotation.validation.InputValidation;
import org.sluja.searcher.webapp.dto.product.ProductDTO;
import org.sluja.searcher.webapp.dto.product.cart.CartDTO;
import org.sluja.searcher.webapp.dto.product.cart.CartProductDTO;
import org.sluja.searcher.webapp.mapper.product.CartProductMapper;
import org.sluja.searcher.webapp.service.user.cart.ICart;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserCartService implements ICart<CartProductDTO, ProductDTO> {

    @Getter
    private final CartDTO cartDTO;
    @Override
    @InputValidation(inputs = {ProductDTO.class})
    public void addProductToCart(final ProductDTO product) {
        //TODO logging
        final CartProductDTO cartProductDTO = CartProductMapper.map(product);
        cartDTO.addProduct(cartProductDTO);
    }

    @Override
    public void removeProductFromCart(final CartProductDTO product) {
        cartDTO.deleteCartProduct(product.getId());
    }

    @Override
    public void clearCart() {
        cartDTO.clearCart();
    }

    @Override
    public void changeQuantity(final CartProductDTO product, final Long quantity) {
        cartDTO.changeQuantity(product.getId(), quantity);
    }

    @Override
    public List<CartProductDTO> getCartProducts() {
        return cartDTO.getProducts()
                .values()
                .stream()
                .toList();
    }
}
