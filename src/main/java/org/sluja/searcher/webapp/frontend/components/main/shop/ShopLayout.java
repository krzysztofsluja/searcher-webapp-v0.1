package org.sluja.searcher.webapp.frontend.components.main.shop;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.sluja.searcher.webapp.dto.presentation.shop.list.ShopDto;
import org.sluja.searcher.webapp.dto.session.MainViewSearchProductsSessionAttribute;
import org.sluja.searcher.webapp.dto.session.MainViewSearchRequestSessionAttribute;
import org.sluja.searcher.webapp.exception.message.IncorrectMessageCodeForReaderException;
import org.sluja.searcher.webapp.exception.message.MessageForGivenKeyNotFoundException;
import org.sluja.searcher.webapp.exception.presentation.SpecificEntityNotFoundException;
import org.sluja.searcher.webapp.exception.presentation.shop.ShopNotFoundException;
import org.sluja.searcher.webapp.service.presentation.shop.list.GetShopService;
import org.sluja.searcher.webapp.utils.message.MessageReader;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Component
@Scope("prototype")
@Qualifier("shopLayout")
@RequiredArgsConstructor
public class ShopLayout extends IShopLayout {

    private final GetShopService getShopService;
    private final MessageReader viewElementMessageReader;
    private final MainViewSearchProductsSessionAttribute mainViewSearchProductsSessionAttribute;
    private final MainViewSearchRequestSessionAttribute mainViewSearchRequestSessionAttribute;

    private List<String> getShopNames(final String context) {
        try {
            if(StringUtils.isNotEmpty(context)) {
                return getShopService.getShopsByContextName(context)
                        .stream()
                        .map(ShopDto::name)
                        .filter(StringUtils::isNotEmpty)
                        .toList();
            }
            throw new ShopNotFoundException();
        } catch (final SpecificEntityNotFoundException e) {
            //TODO logging
            return Collections.emptyList();
        }
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
        final List<String> shopNames = getShopNames(mainViewSearchProductsSessionAttribute.getContext());
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
        checkbox.setValue(isButtonEnabled(shopName));
        checkbox.addValueChangeListener(event -> {
            if(event.getValue()) {
                mainViewSearchProductsSessionAttribute.getSelectedShopNames().add(shopName);
                if(!mainViewSearchProductsSessionAttribute.isOtherCategoryForEachShop()) {
                    final List<String> selectedCategories = mainViewSearchRequestSessionAttribute.getShopsWithCategories()
                            .values()
                            .stream()
                            .flatMap(List::stream)
                            .distinct()
                            .toList();
                    mainViewSearchRequestSessionAttribute.getShopsWithCategories().put(shopName, selectedCategories);
                }
            } else {
                mainViewSearchProductsSessionAttribute.getSelectedShopNames().remove(shopName);
                mainViewSearchProductsSessionAttribute.getOtherShopForEachCategoryCategoryLayoutShowed().put(shopName, false);
                mainViewSearchRequestSessionAttribute.getShopsWithCategories().remove(shopName);
            }
            if(Objects.nonNull(mainViewSearchProductsSessionAttribute.getMainView())) {
                mainViewSearchProductsSessionAttribute.getMainView().refreshShopsWithCategoriesLayout();
            }
        });
        return checkbox;
    }

    private Button getShopButton(final String shopName) {
        final Button button = new Button(">>");
        button.setId(STR."\{shopName}_category_button");
        button.setVisible(mainViewSearchProductsSessionAttribute.isOtherCategoryForEachShop());
        button.setEnabled(isButtonEnabled(shopName));
        button.addClickListener(_ -> {
            final boolean isOtherShopForEachCategory = mainViewSearchProductsSessionAttribute.getOtherShopForEachCategoryCategoryLayoutShowed().getOrDefault(shopName, false);
            mainViewSearchProductsSessionAttribute.getOtherShopForEachCategoryCategoryLayoutShowed().put(shopName, !isOtherShopForEachCategory);
            mainViewSearchProductsSessionAttribute.setActuallyClickedCategoryShopButton(shopName);
            if(Objects.nonNull(mainViewSearchProductsSessionAttribute.getMainView())) {
                mainViewSearchProductsSessionAttribute.getMainView().refreshShopsWithCategoriesLayout();
            }
        });
        return button;
    }

    private boolean isButtonEnabled(final String shopName) {
        return mainViewSearchProductsSessionAttribute.getSelectedShopNames().contains(shopName);
    }

    @Override
    public ShopLayout getShopLayout() {
        removeAll();
        if(StringUtils.isNotEmpty(mainViewSearchProductsSessionAttribute.getContext())) {
            this.add(getShopLabel(), getShopCheckboxesLayout());
        }
        return this;
    }
}
