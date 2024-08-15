package org.sluja.searcher.webapp.frontend.components.main.context;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.sluja.searcher.webapp.frontend.components.main.ILayoutWithContext;

public abstract class IContextLayout extends ILayoutWithContext {

    public abstract VerticalLayout buildContextLayout();
}
