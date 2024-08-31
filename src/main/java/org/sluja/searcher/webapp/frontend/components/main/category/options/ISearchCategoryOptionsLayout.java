package org.sluja.searcher.webapp.frontend.components.main.category.options;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.sluja.searcher.webapp.frontend.components.main.ILayoutWithContext;

public abstract class ISearchCategoryOptionsLayout extends ILayoutWithContext {

    public abstract VerticalLayout getLayout();
    public abstract Component getMainComponent();
}
