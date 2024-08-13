package org.sluja.searcher.webapp.frontend.components.main.category;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.combobox.MultiSelectComboBox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.sluja.searcher.webapp.dto.presentation.category.CategoryDto;
import org.sluja.searcher.webapp.exception.message.IncorrectMessageCodeForReaderException;
import org.sluja.searcher.webapp.exception.message.MessageForGivenKeyNotFoundException;
import org.sluja.searcher.webapp.service.presentation.category.GetCategoryService;
import org.sluja.searcher.webapp.utils.message.MessageReader;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CategoryLayout extends ICategoryLayout{

    private final MessageReader viewElementMessageReader;
    private final GetCategoryService getCategoryService;

    private Text getMainLabel() {
        try {
            final String text = viewElementMessageReader.getPropertyValue("view.main.dashboard.category");
            return new Text(text);
        } catch (MessageForGivenKeyNotFoundException | IncorrectMessageCodeForReaderException e) {
            //todo logging
            return new Text(StringUtils.EMPTY);
        }
    }

    private HorizontalLayout getLayoutWithCategories() {
        final String currentContext = this.getContext();
        if(StringUtils.isEmpty(currentContext)) {
            //todo logging
            return new HorizontalLayout();
        }
        final HorizontalLayout layout = new HorizontalLayout();

        final List<CategoryDto> categories = getCategoryService.getCategoriesByContextName(currentContext);
        final MultiSelectComboBox<CategoryDto> categorySelectComboBox = new MultiSelectComboBox<>(viewElementMessageReader.getPropertyValueOrEmptyOnError("view.main.dashboard.category.lowercase"));
        categorySelectComboBox.setItems(categories);
        categorySelectComboBox.setItemLabelGenerator(CategoryDto::name);
        categorySelectComboBox.setSelectedItemsOnTop(true);

        final TextArea selectedCategoriesTextArea = new TextArea(viewElementMessageReader.getPropertyValueOrEmptyOnError("view.main.dashboard.selected.category"));
        selectedCategoriesTextArea.setReadOnly(true);
        selectedCategoriesTextArea.setHeight(200, Unit.PIXELS);
        categorySelectComboBox.addValueChangeListener(e -> {
            String selectedCategoriesText = e.getValue().stream()
                    .map(CategoryDto::name).collect(Collectors.joining("\n"));
            selectedCategoriesTextArea.setValue(selectedCategoriesText);
        });
        layout.add(categorySelectComboBox, selectedCategoriesTextArea);
        return layout;
    }

    @Override
    public HorizontalLayout getCategoriesLayout() {
        return getLayoutWithCategories();
    }
}
