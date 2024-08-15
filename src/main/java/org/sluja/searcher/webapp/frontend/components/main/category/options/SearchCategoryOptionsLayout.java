package org.sluja.searcher.webapp.frontend.components.main.category.options;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import org.sluja.searcher.webapp.service.frontend.mainview.searchoptions.SearchCategoryOptionsLayoutService;
import org.sluja.searcher.webapp.utils.message.MessageReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Qualifier("searchCategoryOptionsLayout")
public class SearchCategoryOptionsLayout extends ISearchCategoryOptionsLayout {

    private final MessageReader viewElementMessageReader;
    private final String SAME_CATEGORY_FOR_SHOPS;
    private final String OTHER_CATEGORY_FOR_SHOPS;
    private final SearchCategoryOptionsLayoutService searchCategoryOptionsLayoutService;

    @Autowired
    public SearchCategoryOptionsLayout(final MessageReader viewElementMessageReader,
                                       final SearchCategoryOptionsLayoutService searchCategoryOptionsLayoutService) {
        this.viewElementMessageReader = viewElementMessageReader;
        this.searchCategoryOptionsLayoutService = searchCategoryOptionsLayoutService;
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
        final RadioButtonGroup<String> searchOptionsGroup = new RadioButtonGroup<>(getMainLabelText(), searchOptions);
        searchOptionsGroup.addValueChangeListener(event -> {
            final boolean value = SAME_CATEGORY_FOR_SHOPS.equals(event.getValue());
            searchCategoryOptionsLayoutService.publishSearchOptionsChangedEvent(this, value);
        });
        return searchOptionsGroup;
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
}
