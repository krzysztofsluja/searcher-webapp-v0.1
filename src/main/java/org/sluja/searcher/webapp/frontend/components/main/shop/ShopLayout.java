package org.sluja.searcher.webapp.frontend.components.main.shop;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.apache.commons.lang3.StringUtils;
import org.sluja.searcher.webapp.dto.presentation.shop.list.ShopDto;
import org.sluja.searcher.webapp.exception.message.IncorrectMessageCodeForReaderException;
import org.sluja.searcher.webapp.exception.message.MessageForGivenKeyNotFoundException;
import org.sluja.searcher.webapp.exception.presentation.SpecificEntityNotFoundException;
import org.sluja.searcher.webapp.exception.presentation.shop.ShopNotFoundException;
import org.sluja.searcher.webapp.service.presentation.shop.list.GetShopService;
import org.sluja.searcher.webapp.utils.message.MessageReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
@Qualifier("shopLayout")
public class ShopLayout extends IShopLayout {

    private final GetShopService getShopService;
    //private final SearchStateInfo info;
    private final MessageReader viewElementMessageReader;

    @Autowired
    public ShopLayout(final GetShopService getShopService,
                      //final SearchStateInfo searchStateInfo,
                      @Qualifier("viewElementMessageReader") final MessageReader viewElementMessageReader) {
        this.getShopService = getShopService;
        //this.info = searchStateInfo;
        this.viewElementMessageReader = viewElementMessageReader;
        //add(getShopLabel(), getShopCheckboxesLayout());
    }



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
        final List<String> shopNames = getShopNames(this.getContext());
        final int shopAmount = shopNames.size();
        final VerticalLayout layout = new VerticalLayout();

        shopNames
                .forEach(shopName -> {
                    final HorizontalLayout hLayout = new HorizontalLayout();
                    final Checkbox checkbox = new Checkbox(shopName);
                    final Button shopCategoryButton = getShopButton();
                    hLayout.add(checkbox, shopCategoryButton);
                    layout.add(hLayout);
                });
        return layout;
    }

    private Button getShopButton() {
        final Button button = new Button(">>");
        return button;
    }


    @Override
    public ShopLayout getShopLayout() {
        this.add(getShopLabel(), getShopCheckboxesLayout());
        return this;
    }
}
