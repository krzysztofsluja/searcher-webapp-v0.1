package org.sluja.searcher.webapp.frontend.components.main.category;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.combobox.MultiSelectComboBox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.sluja.searcher.webapp.dto.presentation.category.CategoryDto;
import org.sluja.searcher.webapp.dto.session.MainViewSearchProductsSessionAttribute;
import org.sluja.searcher.webapp.dto.session.MainViewSearchRequestSessionAttribute;
import org.sluja.searcher.webapp.exception.message.IncorrectMessageCodeForReaderException;
import org.sluja.searcher.webapp.exception.message.MessageForGivenKeyNotFoundException;
import org.sluja.searcher.webapp.service.presentation.category.GetCategoryService;
import org.sluja.searcher.webapp.utils.message.MessageReader;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Scope("prototype")
@RequiredArgsConstructor
public class CategoryLayout extends ICategoryLayout{

    private final MessageReader viewElementMessageReader;
    private final GetCategoryService getCategoryService;
    private final MainViewSearchProductsSessionAttribute mainViewSearchProductsSessionAttribute;
    private final MainViewSearchRequestSessionAttribute mainViewSearchRequestSessionAttribute;

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
        final String currentContext = mainViewSearchProductsSessionAttribute.getContext();
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
        categorySelectComboBox.setEnabled(!mainViewSearchProductsSessionAttribute.getSelectedShopNames().isEmpty());

        final TextArea selectedCategoriesTextArea = new TextArea(viewElementMessageReader.getPropertyValueOrEmptyOnError("view.main.dashboard.selected.category"));

        if(mainViewSearchProductsSessionAttribute.isOtherCategoryForEachShop()
                && StringUtils.isNotEmpty(mainViewSearchProductsSessionAttribute.getActuallyClickedCategoryShopButton())
                && CollectionUtils.isNotEmpty(mainViewSearchRequestSessionAttribute.getShopsWithCategories().get(mainViewSearchProductsSessionAttribute.getActuallyClickedCategoryShopButton()))) {
            final List<CategoryDto> selectedCategories = getCategoryService.getCategoriesByNames(mainViewSearchRequestSessionAttribute
                    .getShopsWithCategories()
                    .get(mainViewSearchProductsSessionAttribute.getActuallyClickedCategoryShopButton())
                    .stream()
                    .toList());
            final String selectedCategoriesText = String.join("\n", selectedCategories.stream().map(CategoryDto::name).toList());
            categorySelectComboBox.setValue(selectedCategories);
            selectedCategoriesTextArea.setValue(selectedCategoriesText);
        } else if(!mainViewSearchProductsSessionAttribute.isOtherCategoryForEachShop()
        && !mainViewSearchRequestSessionAttribute.getShopsWithCategories().keySet().isEmpty()) {
            final List<CategoryDto> selectedCategories = getCategoryService.getCategoriesByNames(mainViewSearchRequestSessionAttribute
                    .getShopsWithCategories()
                    .values()
                    .stream()
                    .distinct()
                    .flatMap(List::stream)
                    .toList());
            final String selectedCategoriesText = String.join("\n", selectedCategories.stream().map(CategoryDto::name).toList());
            categorySelectComboBox.setValue(selectedCategories);
            selectedCategoriesTextArea.setValue(selectedCategoriesText);
        }


        selectedCategoriesTextArea.setReadOnly(true);
        selectedCategoriesTextArea.setHeight(200, Unit.PIXELS);
        selectedCategoriesTextArea.setEnabled(!mainViewSearchProductsSessionAttribute.getSelectedShopNames().isEmpty());
        categorySelectComboBox.addValueChangeListener(e -> {
            List<String> selectedCategories = e.getValue().stream().map(CategoryDto::name).toList();
            String selectedCategoriesText = String.join("\n", selectedCategories);
            selectedCategoriesTextArea.setValue(selectedCategoriesText);
            if(mainViewSearchProductsSessionAttribute.isOtherCategoryForEachShop()) {
                String shopName = mainViewSearchProductsSessionAttribute.getActuallyClickedCategoryShopButton();
                if(StringUtils.isNotEmpty(shopName)) {
                    mainViewSearchRequestSessionAttribute.getShopsWithCategories().put(shopName, selectedCategories);
                }
            } else {
                mainViewSearchProductsSessionAttribute.getSelectedShopNames()
                        .forEach(shopName -> {
                            mainViewSearchRequestSessionAttribute.getShopsWithCategories().put(shopName, selectedCategories);
                        });
            }



        });
        layout.add(categorySelectComboBox, selectedCategoriesTextArea);
        layout.setVisible(showCategoryLayout());
        return layout;
    }

    private boolean showCategoryLayout() {
        return (!mainViewSearchProductsSessionAttribute.isOtherCategoryForEachShop() && StringUtils.isNotEmpty(mainViewSearchProductsSessionAttribute.getContext()))
                || (StringUtils.isNotEmpty(mainViewSearchProductsSessionAttribute.getActuallyClickedCategoryShopButton())
                && mainViewSearchProductsSessionAttribute.getOtherShopForEachCategoryCategoryLayoutShowed().get(mainViewSearchProductsSessionAttribute.getActuallyClickedCategoryShopButton()));
    }

    @Override
    public HorizontalLayout getCategoriesLayout() {
        removeAll();
        return getLayoutWithCategories();
    }
}
