package org.sluja.searcher.webapp.frontend.components.main.category.options;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import lombok.Getter;
import org.sluja.searcher.webapp.utils.message.MessageReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Scope("prototype")
@Getter
@Qualifier("searchCategoryOptionsLayout")
public class SearchCategoryOptionsLayout extends ISearchCategoryOptionsLayout {

    private final MessageReader viewElementMessageReader;
    private final String SAME_CATEGORY_FOR_SHOPS;
    private final String OTHER_CATEGORY_FOR_SHOPS;
    private final RadioButtonGroup<String> radioButtonGroup = new RadioButtonGroup<>();

    @Autowired
    public SearchCategoryOptionsLayout(final MessageReader viewElementMessageReader) {
        this.viewElementMessageReader = viewElementMessageReader;
        this.SAME_CATEGORY_FOR_SHOPS = viewElementMessageReader.getPropertyValueOrEmptyOnError("view.main.dashboard.category.search.options.same.category");
        this.OTHER_CATEGORY_FOR_SHOPS = viewElementMessageReader.getPropertyValueOrEmptyOnError("view.main.dashboard.category.search.options.other.category");
    }

    private Text getMainLabel() {
        return new Text(viewElementMessageReader.getPropertyValueOrEmptyOnError("view.main.dashboard.category.search.options.label"));
    }

    private String getMainLabelText() {
        return viewElementMessageReader.getPropertyValueOrEmptyOnError("view.main.dashboard.category.search.options.label");
    }

    private Checkbox getSameCategoryCheckbox() {
        final String sameCategoryLabelText = viewElementMessageReader.getPropertyValueOrEmptyOnError("view.main.dashboard.category.search.options.same.category");
        return new Checkbox(sameCategoryLabelText);
    }

    private Checkbox getOtherCategoryCheckbox() {
        final String otherCategoryLabelText = viewElementMessageReader.getPropertyValueOrEmptyOnError("view.main.dashboard.category.search.options.other.category");
        return new Checkbox(otherCategoryLabelText);
    }

    private RadioButtonGroup<String> getSearchOptions() {
        final List<String> searchOptions = new ArrayList<>();
        searchOptions.add(SAME_CATEGORY_FOR_SHOPS);
        searchOptions.add(OTHER_CATEGORY_FOR_SHOPS);
        radioButtonGroup.setLabel(getMainLabelText());
        radioButtonGroup.setItems(searchOptions);
        radioButtonGroup.setValue(SAME_CATEGORY_FOR_SHOPS);
        return radioButtonGroup;
    }

    private VerticalLayout getSearchOptionsLayout() {
        final VerticalLayout layout = new VerticalLayout();
        layout.add(getSameCategoryCheckbox(), getOtherCategoryCheckbox());
        return layout;
    }
    @Override
    public VerticalLayout getLayout() {
        final VerticalLayout layout = new VerticalLayout();
        layout.add(getSearchOptions());
        return layout;
    }

    @Override
    public com.vaadin.flow.component.Component getMainComponent() {
        return radioButtonGroup;
    }
}
