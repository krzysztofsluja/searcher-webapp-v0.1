package org.sluja.searcher.webapp.dto.session.frontend.cart;

import lombok.Getter;
import lombok.Setter;
import org.sluja.searcher.webapp.dto.cart.UserCartProductDto;
import org.sluja.searcher.webapp.frontend.route.cart.UserCartView;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.util.ArrayList;
import java.util.List;

@Component
@SessionScope
@Getter
public class UserCartSessionAttribute {

    private String id;
    private List<UserCartProductDto> cartProducts = new ArrayList<>();
    @Setter
    private UserCartView userCartView;
    @Setter
    private boolean tabbedShopView;
}
