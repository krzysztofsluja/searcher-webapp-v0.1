package org.sluja.searcher.webapp.frontend.components.cart.summary;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import lombok.RequiredArgsConstructor;
import org.sluja.searcher.webapp.dto.cart.UserCartProductsForShopDto;
import org.sluja.searcher.webapp.service.frontend.cart.UserCartLayoutService;
import org.sluja.searcher.webapp.utils.message.MessageReader;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Scope("prototype")
@RequiredArgsConstructor
public class UserCartSummaryLayout extends ICartSummaryLayout {

    private final MessageReader viewElementMessageReader;
    private final UserCartLayoutService userCartLayoutService;

    @Override
    public VerticalLayout build() {
        final VerticalLayout layout = new VerticalLayout();
        layout.add(getCartItemsAmountLayout());
        layout.add(getCartItemsSummaryForShop());
        layout.add(getCartItemsSumPriceLayout());
        return layout;
    }

    private HorizontalLayout getCartItemsAmountLayout() {
        final HorizontalLayout summaryLayout = new HorizontalLayout();
        final String amountTextLayout = viewElementMessageReader.getPropertyValueOrEmptyOnError("view.cart.summary.items.amount");
        final Text label = new Text(amountTextLayout);
        final Text amountLabel = new Text(String.valueOf(userCartLayoutService.getCartItemsAmount()));
        summaryLayout.add(label, amountLabel);
        return summaryLayout;
    }

    private VerticalLayout getCartItemsSummaryForShop() {
        final VerticalLayout layout = new VerticalLayout();
        final List<UserCartProductsForShopDto> cartProductsForShops = userCartLayoutService.getSummaryForShops();
        for(final UserCartProductsForShopDto cartProductsForShop : cartProductsForShops) {
            final HorizontalLayout hLayout = new HorizontalLayout();
            hLayout.add(new Text(cartProductsForShop.getShopName()));
            hLayout.add(new Text(String.valueOf(cartProductsForShop.getProductsQuantity())));
            hLayout.add(new Text(String.valueOf(cartProductsForShop.getProductsPrice())));
            layout.add(hLayout);
        }
        return layout;
    }

    private HorizontalLayout getCartItemsSumPriceLayout() {
        final HorizontalLayout summaryLayout = new HorizontalLayout();
        final String sumPriceText = viewElementMessageReader.getPropertyValueOrEmptyOnError("view.cart.summary.sum.price");
        final Text label = new Text(sumPriceText);
        final Text sumPriceLabel = new Text(String.valueOf(userCartLayoutService.getTotalPrice()));
        summaryLayout.add(label, sumPriceLabel);
        return summaryLayout;
    }
}
