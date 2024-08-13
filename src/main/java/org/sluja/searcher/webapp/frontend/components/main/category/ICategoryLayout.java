package org.sluja.searcher.webapp.frontend.components.main.category;

import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import lombok.Getter;
import lombok.Setter;
import org.sluja.searcher.webapp.frontend.components.main.ILayoutWithContext;

public abstract class ICategoryLayout extends ILayoutWithContext {

    @Setter
    @Getter
    protected boolean sameCategoryForEachShop;

    public abstract HorizontalLayout getCategoriesLayout();
}
