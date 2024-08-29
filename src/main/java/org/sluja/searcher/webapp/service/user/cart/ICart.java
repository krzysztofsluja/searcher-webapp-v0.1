package org.sluja.searcher.webapp.service.user.cart;

import java.util.List;

public interface ICart<R, T> {
    void addProductToCart(T product);
    void removeProductFromCart(R product);
    void clearCart();
    void changeQuantity(R product, int quantity);
    List<R> getCartProducts();
}
