package org.sluja.searcher.webapp.frontend.components.cart.main;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.TabSheet;
import com.vaadin.flow.component.textfield.BigDecimalField;
import com.vaadin.flow.component.textfield.IntegerField;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.sluja.searcher.webapp.dto.cart.UserCartProductDto;
import org.sluja.searcher.webapp.service.frontend.cart.main.UserCartMainLayoutService;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class UserCartMainLayout extends ICartLayout {

    private final UserCartMainLayoutService userCartMainLayoutService;

    private HorizontalLayout getCartProductRow(final UserCartProductDto cartProduct) {
        final Anchor productLink = new Anchor(cartProduct.getProduct().productPageAddress().getFirst(), cartProduct.getProduct().name());
        productLink.setTarget("_blank");
        final Div nameLabel = new Div(productLink);
        final Text shopNameLabel = new Text(cartProduct.getProduct().shopName());
        final Icon crossIcon = new Icon("lumo", "cross");
        final Text productPriceText = new Text(String.valueOf(cartProduct.getProduct().price()));
        return new HorizontalLayout(nameLabel, shopNameLabel, getQuantityField(cartProduct), crossIcon, productPriceText, getProductPriceField(cartProduct), getRemoveProductButton(cartProduct));
    }

    private Map<String, List<HorizontalLayout>> getCartProductRowForShop(final List<UserCartProductDto> cartProducts) {
        final Map<String, List<HorizontalLayout>> cartProductsByShop = new HashMap<>();
        if(CollectionUtils.isNotEmpty(cartProducts)) {
            for (final UserCartProductDto cartProduct : cartProducts) {
                final String shopName = cartProduct.getProduct().shopName();
                cartProductsByShop.computeIfAbsent(shopName, _ -> new ArrayList<>());
                final List<HorizontalLayout> shopRows = cartProductsByShop.get(shopName);
                shopRows.add(getCartProductRow(cartProduct));
            }
        }
        return cartProductsByShop;
    }

    private Button getRemoveProductButton(final UserCartProductDto cartProduct) {
        final Button removeButton = new Button(VaadinIcon.FILE_REMOVE.create());
        removeButton.addClickListener(_ -> {
            userCartMainLayoutService.removeProductFromCart(cartProduct);
        });
        return removeButton;
    }

    private IntegerField getQuantityField(final UserCartProductDto cartProduct) {
        final IntegerField quantityField = new IntegerField();
        quantityField.setStepButtonsVisible(true);
        quantityField.setValue(cartProduct.getQuantity());
        quantityField.setMin(1);
        quantityField.setMax(100);
        quantityField.addValueChangeListener(e ->userCartMainLayoutService.changeQuantity(cartProduct.getCartProductId(), e.getValue()));
        return quantityField;
    }

    private BigDecimalField getProductPriceField(final UserCartProductDto cartProduct) {
        final BigDecimalField priceField = new BigDecimalField();
        priceField.setValue(cartProduct.getProduct().price().multiply(new BigDecimal(cartProduct.getQuantity())));
        priceField.setReadOnly(true);
        return priceField;
    }

    private VerticalLayout getTabbedForShopsLayout() {
        final VerticalLayout layout = new VerticalLayout();
        final TabSheet tabSheet = new TabSheet();
        final Map<String, List<HorizontalLayout>> cartProductsByShop = getCartProductRowForShop(userCartMainLayoutService.getCartProducts());
        for(final Map.Entry<String, List<HorizontalLayout>> entry : cartProductsByShop.entrySet()) {
            tabSheet.add(entry.getKey(), getCartProductsList(entry.getValue()));
        }
        layout.add(tabSheet);
        return layout;
    }

    private final VerticalLayout getCartProductsList(final List<HorizontalLayout> cartProductRows) {
        final VerticalLayout layout = new VerticalLayout();
        for(final HorizontalLayout cartProductRow : cartProductRows) {
            layout.add(cartProductRow);
        }
        return layout;
    }

    @Override
    public VerticalLayout build() {
        final VerticalLayout layout = new VerticalLayout();
        if(userCartMainLayoutService.isTabbedViewActivated()) {
            layout.add(getTabbedForShopsLayout());
            return layout;
        }
        //userCartSessionAttribute.getCartProducts().add(new UserCartProductDto("1", 2, new ProductDTO("9da1433a-f3b3-4e45-bb71-9fe36252c6d6", "Rama Federal Perrin ICS", "manyfestbmx", new BigDecimal(1349), List.of("https://manyfestbmx.pl/rama-bmx-federal-perrin-ics#/660-kolor-brazowy_mat/741-rozmiar_ramy-2075_"), List.of("https://manyfestbmx.pl/28066-home_default/rama-bmx-federal-perrin-ics.webp"), "frames", "skate")));
        for(final UserCartProductDto cartProduct : userCartMainLayoutService.getCartProducts()) {
            layout.add(getCartProductRow(cartProduct));
        }
        return layout;
    }

}
