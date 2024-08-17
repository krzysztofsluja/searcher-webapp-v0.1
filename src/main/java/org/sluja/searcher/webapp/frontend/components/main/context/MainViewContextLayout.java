package org.sluja.searcher.webapp.frontend.components.main.context;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import lombok.RequiredArgsConstructor;
import org.sluja.searcher.webapp.dto.context.ContextDto;
import org.sluja.searcher.webapp.dto.session.MainViewSearchProductsSessionAttribute;
import org.sluja.searcher.webapp.service.presentation.context.GetContextService;
import org.sluja.searcher.webapp.utils.message.MessageReader;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
@Qualifier("mainViewContextLayout")
@RequiredArgsConstructor
public class MainViewContextLayout extends IContextLayout {

    private final MainViewSearchProductsSessionAttribute mainViewSearchProductsSessionAttribute;
    private final GetContextService getContextService;
    private final MessageReader viewElementMessageReader;
    private ComboBox<ContextDto> comboBox = new ComboBox<>();

    private String getMainLabelText() {
        return viewElementMessageReader.getPropertyValueOrEmptyOnError("view.main.dashboard.context.lowercase");
    }

    private VerticalLayout getLayout() {
        final VerticalLayout layout = new VerticalLayout();
        final String mainLabelText = getMainLabelText();
        final ComboBox<ContextDto> comboBox = getComboBox(mainLabelText);
        layout.add(comboBox);
        return layout;
    }

    private ComboBox<ContextDto> getComboBox(final String mainLabelText) {
        comboBox.setLabel(mainLabelText);
        comboBox.setItems(getContextService.getAllContexts());
        comboBox.setItemLabelGenerator(ContextDto::name);
        return comboBox;
    }

    public com.vaadin.flow.component.Component getMainComponent() {
        return this.comboBox;
    }

    @Override
    public VerticalLayout buildContextLayout() {
        return getLayout();
    }
}
