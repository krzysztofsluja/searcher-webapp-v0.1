package org.sluja.searcher.webapp.frontend.components.cart.options;

import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import lombok.RequiredArgsConstructor;
import org.sluja.searcher.webapp.service.frontend.cart.options.UserCartOptionsLayoutService;
import org.sluja.searcher.webapp.utils.message.MessageReader;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserCartOptionsLayout extends ICartOptionsLayout {

    private final MessageReader viewElementMessageReader;
    private final UserCartOptionsLayoutService userCartOptionsLayoutService;

    private VerticalLayout getTabbedShopToggleButton() {
        final VerticalLayout layout = new VerticalLayout();
        final Checkbox shopTabbedCheckbox = new Checkbox(viewElementMessageReader.getPropertyValueOrEmptyOnError("view.cart.options.tabbed.shop"));
        shopTabbedCheckbox.setValue(userCartOptionsLayoutService.isTabsForShopsActive());
        shopTabbedCheckbox.addValueChangeListener(event -> {
            userCartOptionsLayoutService.setTabsForShopsActive(event.getValue());
            userCartOptionsLayoutService.refreshView();
        });
        layout.add(shopTabbedCheckbox);
        return layout;
    }
    @Override
    public VerticalLayout build() {
        return new VerticalLayout(getTabbedShopToggleButton());
    }
}
