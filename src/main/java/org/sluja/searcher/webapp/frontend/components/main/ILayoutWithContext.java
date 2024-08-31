package org.sluja.searcher.webapp.frontend.components.main;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import lombok.Getter;
import lombok.Setter;

public abstract class ILayoutWithContext extends VerticalLayout {

    @Setter
    @Getter
    protected String context;
}
