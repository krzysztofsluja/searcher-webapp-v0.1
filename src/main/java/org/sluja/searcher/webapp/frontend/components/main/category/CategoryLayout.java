package org.sluja.searcher.webapp.frontend.components.main.category;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.combobox.MultiSelectComboBox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.sluja.searcher.webapp.dto.presentation.category.CategoryDto;
import org.sluja.searcher.webapp.exception.message.IncorrectMessageCodeForReaderException;
import org.sluja.searcher.webapp.exception.message.MessageForGivenKeyNotFoundException;
import org.sluja.searcher.webapp.service.frontend.category.CategoryLayoutService;
import org.sluja.searcher.webapp.utils.message.MessageReader;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Scope("prototype")
@RequiredArgsConstructor
public class CategoryLayout extends ICategoryLayout{

    private final MessageReader viewElementMessageReader;
    private final CategoryLayoutService categoryLayoutService;

    private Text getMainLabel() {
        try {
            final String text = viewElementMessageReader.getPropertyValue("view.main.dashboard.category");
            return new Text(text);
        } catch (MessageForGivenKeyNotFoundException | IncorrectMessageCodeForReaderException e) {
            //todo logging
            return new Text(StringUtils.EMPTY);
        }
    }

    private MultiSelectComboBox<CategoryDto> getCategorySelectBox() {
        final List<CategoryDto> categories = categoryLayoutService.getCategoriesForCurrentContext();
        final MultiSelectComboBox<CategoryDto> categorySelectComboBox = new MultiSelectComboBox<>(viewElementMessageReader.getPropertyValueOrEmptyOnError("view.main.dashboard.category.lowercase"));
        categorySelectComboBox.setItems(categories);
        categorySelectComboBox.setItemLabelGenerator(CategoryDto::name);
        categorySelectComboBox.setSelectedItemsOnTop(true);
        categorySelectComboBox.setEnabled(categoryLayoutService.isAnyShopSelected());
        return categorySelectComboBox;
    }

    private void setUpComboBoxListener(final MultiSelectComboBox<CategoryDto> categorySelectComboBox,
                                       final TextArea selectedCategoriesTextArea) {
        categorySelectComboBox.addValueChangeListener(e -> {
            List<String> selectedCategories = e.getValue().stream().map(CategoryDto::name).toList();
            String selectedCategoriesText = String.join("\n", selectedCategories);
            selectedCategoriesTextArea.setValue(selectedCategoriesText);
            categoryLayoutService.setSelectedCategories(selectedCategories);
        });
    }

    private TextArea getSelectedCategoriesTextArea() {
        final TextArea selectedCategoriesTextArea = new TextArea(viewElementMessageReader.getPropertyValueOrEmptyOnError("view.main.dashboard.selected.category"));
        selectedCategoriesTextArea.setReadOnly(true);
        selectedCategoriesTextArea.setHeight(200, Unit.PIXELS);
        selectedCategoriesTextArea.setEnabled(categoryLayoutService.isAnyShopSelected());
        return selectedCategoriesTextArea;
    }

    private HorizontalLayout getLayoutWithCategories() {
        final String currentContext = categoryLayoutService.getCurrentSearchContext();
        if(StringUtils.isEmpty(currentContext)) {
            //todo logging
            return new HorizontalLayout();
        }
        final HorizontalLayout layout = new HorizontalLayout();
        final MultiSelectComboBox<CategoryDto> categorySelectComboBox = getCategorySelectBox();
        final TextArea selectedCategoriesTextArea = getSelectedCategoriesTextArea();

        if(categoryLayoutService.shouldSpecificCategoriesForShopBeShown()) {
            final List<CategoryDto> selectedCategories = categoryLayoutService.getCategories(false);
            fillUpCategoryComponents(selectedCategoriesTextArea, categorySelectComboBox, selectedCategories);
        } else if(categoryLayoutService.shouldSameCategoriesForShopsBeShown()) {
            final List<CategoryDto> selectedCategories = categoryLayoutService.getCategories(true);
            fillUpCategoryComponents(selectedCategoriesTextArea, categorySelectComboBox, selectedCategories);
        }

        setUpComboBoxListener(categorySelectComboBox, selectedCategoriesTextArea);

        layout.add(categorySelectComboBox, selectedCategoriesTextArea);
        layout.setVisible(categoryLayoutService.shouldCategoryLayoutBeVisible());
        return layout;
    }

    private VerticalLayout getLayoutWithLabelAndCategories() {
        final VerticalLayout layout = new VerticalLayout();
        layout.add(categoryLayoutService.getSelectedCurrentShop());
        layout.add(getLayoutWithCategories());
        layout.setVisible(categoryLayoutService.shouldCategoryLayoutBeVisible());
        return layout;
    }

    private void fillUpCategoryComponents(final TextArea selectedCategoriesTextArea,
                                          final MultiSelectComboBox<CategoryDto> categorySelectComboBox,
                                          final List<CategoryDto> selectedCategories) {
        final String selectedCategoriesText = categoryLayoutService.getSelectedCategoriesText(selectedCategories);
        categorySelectComboBox.setValue(selectedCategories);
        selectedCategoriesTextArea.setValue(selectedCategoriesText);
    }

    @Override
    public HorizontalLayout getCategoriesLayout() {
        removeAll();
        if(categoryLayoutService.isIndividualCategoriesForEachShop()) {
            return new HorizontalLayout(getLayoutWithLabelAndCategories());
        }
        return getLayoutWithCategories();
    }
}
