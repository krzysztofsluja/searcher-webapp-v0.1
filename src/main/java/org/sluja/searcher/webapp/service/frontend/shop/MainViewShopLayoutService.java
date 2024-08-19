package org.sluja.searcher.webapp.service.frontend.shop;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.sluja.searcher.webapp.dto.presentation.shop.list.ShopDto;
import org.sluja.searcher.webapp.dto.session.MainViewSearchRequestSessionAttribute;
import org.sluja.searcher.webapp.dto.session.frontend.mainview.MainViewSearchContextForUserSessionAttribute;
import org.sluja.searcher.webapp.exception.presentation.SpecificEntityNotFoundException;
import org.sluja.searcher.webapp.exception.presentation.shop.ShopNotFoundException;
import org.sluja.searcher.webapp.service.presentation.shop.list.GetShopService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class MainViewShopLayoutService {

    private final GetShopService getShopService;
    private final MainViewSearchContextForUserSessionAttribute mainViewSearchContextForUserSessionAttribute;
    private final MainViewSearchRequestSessionAttribute mainViewSearchRequestSessionAttribute;

    public List<ShopDto> getShopsForContext(final String contextName) {
        try {
            if(StringUtils.isEmpty(contextName)) {
                throw new ShopNotFoundException();
            }
            return getShopService.getShopsByContextName(contextName);
        } catch (final SpecificEntityNotFoundException e) {
            //todo logging
            return Collections.emptyList();
        }
    }

    public List<String> getShopsNamesForContext(final String contextName) {
        return getShopsForContext(contextName)
                .stream()
                .map(ShopDto::name)
                .toList();
    }

    private void setShopSelection(final boolean isShopSelected, final String shopName) {
        final Set<String> selectedShopsSet = mainViewSearchContextForUserSessionAttribute.getSelectedShopsNames();
        if(Objects.nonNull(selectedShopsSet) && StringUtils.isNotEmpty(shopName)) {
            if(isShopSelected) {
                selectedShopsSet.add(shopName);
            } else {
                selectedShopsSet.remove(shopName);
            }
        }
    }

    private void setSelectedCategoriesForShop(final String shopName) {
        if(!mainViewSearchContextForUserSessionAttribute.isOtherCategoriesForEachShop()) {
            final List<String> selectedCategories = mainViewSearchRequestSessionAttribute.getShopsWithCategories()
                    .values()
                    .stream()
                    .flatMap(List::stream)
                    .distinct()
                    .toList();
            mainViewSearchRequestSessionAttribute.getShopsWithCategories().put(shopName, selectedCategories);
        }
    }

    public void executeActionOnSelectedShop(final String shopName, final boolean isShopSelected) {
        if(isShopSelected) {
            setShopSelection(true, shopName);
            setSelectedCategoriesForShop(shopName);
        } else {
            setShopSelection(false, shopName);
            mainViewSearchContextForUserSessionAttribute.getCategoryLayoutVisibilityForShop().put(shopName, false);
            mainViewSearchRequestSessionAttribute.getShopsWithCategories().remove(shopName);
        }
    }

    public boolean isShopSelected(final String shopName) {
        return mainViewSearchContextForUserSessionAttribute.getSelectedShopsNames().contains(shopName);
    }

    public boolean shouldButtonForShopBeVisible() {
        return mainViewSearchContextForUserSessionAttribute.isOtherCategoriesForEachShop();
    }

    public void executeActionOnClickOtherCategoryForShop(final String shopName) {
        final boolean isOtherShopForEachCategory = mainViewSearchContextForUserSessionAttribute
                .getCategoryLayoutVisibilityForShop()
                .getOrDefault(shopName, false);
        mainViewSearchContextForUserSessionAttribute.getCategoryLayoutVisibilityForShop().put(shopName, !isOtherShopForEachCategory);
        mainViewSearchContextForUserSessionAttribute.setShopForWhichCategoryLayoutIsShown(shopName);
    }

    public String getCurrentContext() {
        return mainViewSearchContextForUserSessionAttribute.getCategoryContext();
    }
}
