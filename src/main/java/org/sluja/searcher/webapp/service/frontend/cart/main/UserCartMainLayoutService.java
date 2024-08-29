package org.sluja.searcher.webapp.service.frontend.cart.main;

import lombok.RequiredArgsConstructor;
import org.sluja.searcher.webapp.dto.cart.UserCartProductDto;
import org.sluja.searcher.webapp.dto.session.frontend.cart.UserCartSessionAttribute;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserCartMainLayoutService {

    private final UserCartSessionAttribute userCartSessionAttribute;

    public void removeProductFromCart(final UserCartProductDto cartProduct) {
        userCartSessionAttribute.getCartProducts().remove(cartProduct);
        userCartSessionAttribute.getUserCartView().refreshView();
    }

    public void changeQuantity(final String cartProductId, final int value) {
        userCartSessionAttribute.getCartProducts().stream()
                .filter(cartProduct -> cartProduct.getCartProductId().equalsIgnoreCase(cartProductId))
                .forEach(cartProduct -> {
                    cartProduct.setQuantity(value);
                    if(value <= 0) {
                        userCartSessionAttribute.getCartProducts().remove(cartProduct);
                    }
                });
        userCartSessionAttribute.getUserCartView().refreshView();
    }

    public List<UserCartProductDto> getCartProducts() {
        return userCartSessionAttribute.getCartProducts();
    }

}
