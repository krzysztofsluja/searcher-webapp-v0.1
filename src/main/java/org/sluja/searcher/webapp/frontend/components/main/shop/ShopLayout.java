package org.sluja.searcher.webapp.frontend.components.main.shop;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.sluja.searcher.webapp.exception.message.IncorrectMessageCodeForReaderException;
import org.sluja.searcher.webapp.exception.message.MessageForGivenKeyNotFoundException;
import org.sluja.searcher.webapp.service.frontend.shop.MainViewShopLayoutService;
import org.sluja.searcher.webapp.service.frontend.view.mainview.MainViewService;
import org.sluja.searcher.webapp.utils.message.MessageReader;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Scope("prototype")
@Qualifier("shopLayout")
@RequiredArgsConstructor
public class ShopLayout extends IShopLayout {

    private final MessageReader viewElementMessageReader;
    private final MainViewShopLayoutService mainViewShopLayoutService;
    private final MainViewService mainViewService;

    private List<String> getShopNames(final String context) {
        return mainViewShopLayoutService.getShopsNamesForContext(context);
    }

    private Div getShopLabel() {
        final Div shopLabel = new Div();
        String shopLabelTextMessage;
        try {
            shopLabelTextMessage = viewElementMessageReader.getPropertyValue("view.main.dashboard.shop");
        } catch (IncorrectMessageCodeForReaderException | MessageForGivenKeyNotFoundException e) {
            shopLabelTextMessage = StringUtils.EMPTY;
        }
        final Text shopLabelText = new Text(shopLabelTextMessage);
        shopLabel.add(shopLabelText);
        return shopLabel;
    }

    private VerticalLayout getShopCheckboxesLayout() {
        final List<String> shopNames = getShopNames(mainViewShopLayoutService.getCurrentContext());
        final VerticalLayout layout = new VerticalLayout();
        shopNames
                .forEach(shopName -> {
                    final HorizontalLayout hLayout = new HorizontalLayout();
                    final Checkbox checkbox = getCheckbox(shopName);
                    final Button shopCategoryButton = getShopButton(shopName);
                    hLayout.add(checkbox, shopCategoryButton);
                    layout.add(hLayout);
                });
        return layout;
    }

    private Checkbox getCheckbox(final String shopName) {
        final Checkbox checkbox = new Checkbox(shopName);
        checkbox.setValue(mainViewShopLayoutService.isShopSelected(shopName));
        checkbox.addValueChangeListener(event -> {
            mainViewShopLayoutService.executeActionOnSelectedShop(shopName, event.getValue());
            mainViewService.refreshShopsWithCategoriesLayout();
        });
        return checkbox;
    }

    private Button getShopButton(final String shopName) {
        final Button button = new Button(">>");
        button.setId(STR."\{shopName}_category_button");
        button.setVisible(mainViewShopLayoutService.shouldButtonForShopBeVisible());
        button.setEnabled(mainViewShopLayoutService.isShopSelected(shopName));
        button.addClickListener(_ -> {
            mainViewShopLayoutService.executeActionOnClickOtherCategoryForShop(shopName);
            mainViewService.refreshShopsWithCategoriesLayout();
        });
        return button;
    }

    @Override
    public ShopLayout getShopLayout() {
        removeAll();
        if(StringUtils.isNotEmpty(mainViewShopLayoutService.getCurrentContext())) {
            this.add(getShopLabel(), getShopCheckboxesLayout());
        }
        return this;
    }
}
