package org.sluja.searcher.webapp.dto.session.frontend.cart;

import lombok.Getter;
import org.sluja.searcher.webapp.dto.cart.UserCartProductDto;
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
}
