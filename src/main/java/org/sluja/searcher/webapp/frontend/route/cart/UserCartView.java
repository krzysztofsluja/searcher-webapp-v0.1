package org.sluja.searcher.webapp.frontend.route.cart;

import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PreserveOnRefresh;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.sluja.searcher.webapp.dto.session.frontend.cart.UserCartSessionAttribute;
import org.sluja.searcher.webapp.frontend.components.cart.main.ICartLayout;
import org.sluja.searcher.webapp.frontend.components.cart.options.ICartOptionsLayout;
import org.sluja.searcher.webapp.frontend.components.cart.summary.ICartSummaryLayout;
import org.springframework.beans.factory.annotation.Autowired;

@Route("/public/cart")
@AnonymousAllowed
@PreserveOnRefresh
public class UserCartView extends HorizontalLayout {

    private final UserCartSessionAttribute userCartSessionAttribute;
    private final ICartSummaryLayout userCartSummaryLayout;
    private final ICartLayout userCartMainLayout;
    private final ICartOptionsLayout userCartOptionsLayout;
    @Autowired
    public UserCartView(final UserCartSessionAttribute userCartSessionAttribute,
                        final ICartSummaryLayout userCartSummaryLayout,
                        final ICartLayout userCartMainLayout,
                        final ICartOptionsLayout userCartOptionsLayout) {
        this.userCartSummaryLayout = userCartSummaryLayout;
        this.userCartSessionAttribute = userCartSessionAttribute;
        this.userCartMainLayout = userCartMainLayout;
        this.userCartOptionsLayout = userCartOptionsLayout;
        userCartSessionAttribute.setUserCartView(this);
        buildView();
    }

    private void buildView() {
        this.add(userCartMainLayout.build(), getSideLayout());
    }

    private VerticalLayout getSideLayout() {
        return new VerticalLayout(userCartOptionsLayout.build(), userCartSummaryLayout.build());
    }

    public void refreshView() {
        removeAll();
        buildView();
    }


}
