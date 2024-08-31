package org.sluja.searcher.webapp.service.frontend.cart.options;

import lombok.RequiredArgsConstructor;
import org.sluja.searcher.webapp.dto.session.frontend.cart.UserCartSessionAttribute;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserCartOptionsLayoutService {

    private final UserCartSessionAttribute userCartSessionAttribute;

    public void setTabsForShopsActive(final boolean value) {
        userCartSessionAttribute.setTabbedShopView(value);
    }



    public void refreshView() {
        userCartSessionAttribute.getUserCartView().refreshView();
    }

    public Boolean isTabsForShopsActive() {
        return userCartSessionAttribute.isTabbedShopView();
    }
}
