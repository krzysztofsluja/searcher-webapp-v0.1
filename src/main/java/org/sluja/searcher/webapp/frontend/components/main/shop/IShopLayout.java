package org.sluja.searcher.webapp.frontend.components.main.shop;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import lombok.Getter;
import lombok.Setter;

public abstract class IShopLayout extends VerticalLayout {

    @Setter
    @Getter
    protected String context;

    public abstract IShopLayout getShopLayout();
}
