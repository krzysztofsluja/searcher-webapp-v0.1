package org.sluja.searcher.webapp.service.frontend.category;

import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.sluja.searcher.webapp.dto.presentation.category.CategoryDto;
import org.sluja.searcher.webapp.dto.session.MainViewSearchRequestSessionAttribute;
import org.sluja.searcher.webapp.dto.session.frontend.mainview.MainViewSearchContextForUserSessionAttribute;
import org.sluja.searcher.webapp.service.presentation.category.GetCategoryService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryLayoutService {

    private final MainViewSearchRequestSessionAttribute mainViewSearchRequestSessionAttribute;
    private final MainViewSearchContextForUserSessionAttribute mainViewSearchContextForUserSessionAttribute;
    private final GetCategoryService getCategoryService;

    public String getCurrentSearchContext() {
        return mainViewSearchContextForUserSessionAttribute.getCategoryContext();
    }

    public List<CategoryDto> getCategoriesForCurrentContext() {
        final String currentContext = getCurrentSearchContext();
        if(StringUtils.isEmpty(currentContext)) {
            return Collections.emptyList();
        }
        return getCategoryService.getCategoriesByContextName(currentContext);
    }

    public boolean isAnyShopSelected() {
        return !mainViewSearchContextForUserSessionAttribute.getSelectedShopsNames().isEmpty();
    }

    public boolean shouldSpecificCategoriesForShopBeShown() {
        final String currentSelectedShop = mainViewSearchContextForUserSessionAttribute.getShopForWhichCategoryLayoutIsShown();
        return mainViewSearchContextForUserSessionAttribute.isOtherCategoriesForEachShop()
                && StringUtils.isNotEmpty(currentSelectedShop)
                && CollectionUtils.isNotEmpty(mainViewSearchRequestSessionAttribute.getShopsWithCategories().get(currentSelectedShop));
    }

    public boolean shouldSameCategoriesForShopsBeShown() {
        return !mainViewSearchContextForUserSessionAttribute.isOtherCategoriesForEachShop()
                && !mainViewSearchRequestSessionAttribute.getShopsWithCategories().keySet().isEmpty();
    }

    public List<CategoryDto> getCategories(final boolean shouldAllCategoriesBeConsidered) {
        if(shouldAllCategoriesBeConsidered) {
            return getCategoryService.getCategoriesByNames(mainViewSearchRequestSessionAttribute
                    .getShopsWithCategories()
                    .values()
                    .stream()
                    .distinct()
                    .flatMap(List::stream)
                    .toList());
        }
        return getCategoryService.getCategoriesByNames(mainViewSearchRequestSessionAttribute
                .getShopsWithCategories()
                .get(mainViewSearchContextForUserSessionAttribute.getShopForWhichCategoryLayoutIsShown())
                .stream()
                .toList());
    }

    public String getSelectedCategoriesText(final List<CategoryDto> selectedCategories) {
        return String.join("\n", selectedCategories
                .stream()
                .map(CategoryDto::name)
                .toList());
    }

    public boolean shouldCategoryLayoutBeVisible() {
        return (!mainViewSearchContextForUserSessionAttribute.isOtherCategoriesForEachShop() && StringUtils.isNotEmpty(mainViewSearchContextForUserSessionAttribute.getCategoryContext()))
                || (StringUtils.isNotEmpty(mainViewSearchContextForUserSessionAttribute.getShopForWhichCategoryLayoutIsShown())
                && mainViewSearchContextForUserSessionAttribute.getCategoryLayoutVisibilityForShop().get(mainViewSearchContextForUserSessionAttribute.getShopForWhichCategoryLayoutIsShown()));
    }

    public boolean isIndividualCategoriesForEachShop() {
        return mainViewSearchContextForUserSessionAttribute.isOtherCategoriesForEachShop();
    }

    public void setSelectedCategories(final List<String> selectedCategories) {
        if(isIndividualCategoriesForEachShop()) {
            final String shopName = mainViewSearchContextForUserSessionAttribute.getShopForWhichCategoryLayoutIsShown();
            if(StringUtils.isNotEmpty(shopName)) {
                mainViewSearchRequestSessionAttribute.getShopsWithCategories().put(shopName, selectedCategories);
            }
        } else {
             mainViewSearchContextForUserSessionAttribute.getSelectedShopsNames()
                    .forEach(shopName -> {
                        mainViewSearchRequestSessionAttribute.getShopsWithCategories().put(shopName, selectedCategories);
                    });
        }
    }
}
