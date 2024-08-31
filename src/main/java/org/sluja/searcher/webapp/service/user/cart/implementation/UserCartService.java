package org.sluja.searcher.webapp.service.user.cart.implementation;

import lombok.RequiredArgsConstructor;
import org.sluja.searcher.webapp.dto.cart.UserCartProductDto;
import org.sluja.searcher.webapp.dto.product.ProductDTO;
import org.sluja.searcher.webapp.dto.session.frontend.cart.UserCartSessionAttribute;
import org.sluja.searcher.webapp.mapper.product.CartProductMapper;
import org.sluja.searcher.webapp.service.user.cart.ICart;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserCartService implements ICart<UserCartProductDto, ProductDTO> {

    private final UserCartSessionAttribute userCartSessionAttribute;
    @Override
    public void addProductToCart(final ProductDTO product) {
        if(!doesCartContainProduct(product)) {
            userCartSessionAttribute.getCartProducts().add(CartProductMapper.map(product));
        } else {
            userCartSessionAttribute.getCartProducts().stream()
                    .filter(cartProduct -> cartProduct.getProduct().id().equals(product.id()))
                    .forEach(cartProduct -> cartProduct.setQuantity(cartProduct.getQuantity() + 1));
        }
    }

    private boolean doesCartContainProduct(final ProductDTO product) {
        return getCartProducts().stream()
                .anyMatch(cartProduct -> cartProduct.getProduct().name().equalsIgnoreCase(product.name())
                                      && cartProduct.getProduct().shopName().equalsIgnoreCase(product.shopName()));
    }

    @Override
    public void removeProductFromCart(UserCartProductDto product) {
        userCartSessionAttribute.getCartProducts().remove(product);
    }

    @Override
    public void clearCart() {
        userCartSessionAttribute.getCartProducts().clear();
    }

    @Override
    public void changeQuantity(final UserCartProductDto product, final int quantity) {
        userCartSessionAttribute.getCartProducts().stream()
                .filter(cartProduct -> cartProduct.getProduct().id().equals(product.getCartProductId()))
                .forEach(cartProduct -> cartProduct.setQuantity(cartProduct.getQuantity() + quantity));
    }

    @Override
    public List<UserCartProductDto> getCartProducts() {
        return userCartSessionAttribute.getCartProducts();
    }
/*
    @Getter
    private final CartDTO cartDTO;
    @Override
    @InputValidation(inputs = {ProductDTO.class})
    @ObjectMethodStartLog
    public void addProductToCart(final ProductDTO product) {
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
    }*/
}
