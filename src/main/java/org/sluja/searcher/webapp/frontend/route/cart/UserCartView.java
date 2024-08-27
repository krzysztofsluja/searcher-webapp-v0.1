package org.sluja.searcher.webapp.frontend.route.cart;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PreserveOnRefresh;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.sluja.searcher.webapp.dto.session.frontend.cart.UserCartSessionAttribute;
import org.sluja.searcher.webapp.frontend.components.cart.summary.ICartSummaryLayout;
import org.springframework.beans.factory.annotation.Autowired;

@Route("/public/cart")
@AnonymousAllowed
@PreserveOnRefresh
public class UserCartView extends VerticalLayout {

    private final UserCartSessionAttribute userCartSessionAttribute;
    private final ICartSummaryLayout userCartSummaryLayout;
    @Autowired
    public UserCartView(final UserCartSessionAttribute userCartSessionAttribute,
                        final ICartSummaryLayout userCartSummaryLayout) {
        this.userCartSummaryLayout = userCartSummaryLayout;
        this.userCartSessionAttribute = userCartSessionAttribute;
        add(userCartSummaryLayout.build());
    }


}
