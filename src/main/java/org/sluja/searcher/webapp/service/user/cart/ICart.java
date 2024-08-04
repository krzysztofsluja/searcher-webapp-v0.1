package org.sluja.searcher.webapp.service.user.cart;

import jakarta.servlet.http.HttpSession;

import java.util.List;

public interface ICart<R, T> {
    void addProductToCart(T product);
    void removeProductFromCart(R product);
    void clearCart();
    void changeQuantity(R product, Long quantity);
    List<R> getCartProducts();
}
