package org.sluja.searcher.webapp.service.frontend.category.options;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.sluja.searcher.webapp.dto.session.MainViewSearchRequestSessionAttribute;
import org.sluja.searcher.webapp.dto.session.frontend.mainview.MainViewSearchContextForUserSessionAttribute;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class MainViewSearchCategoryOptionsLayoutService {

    private final MainViewSearchRequestSessionAttribute mainViewSearchRequestSessionAttribute;
    private final MainViewSearchContextForUserSessionAttribute mainViewSearchContextForUserSessionAttribute;

    public void setSearchOption(final boolean otherCategoryForEachShop) {
        mainViewSearchContextForUserSessionAttribute.setOtherCategoriesForEachShop(otherCategoryForEachShop);
    }

    public void clearPreviousSearchContextOnChange() {
        final Map<String, Boolean> categoryLayoutVisibilityForShops = mainViewSearchContextForUserSessionAttribute.getCategoryLayoutVisibilityForShop();
        if(!categoryLayoutVisibilityForShops.keySet().isEmpty()) {
            categoryLayoutVisibilityForShops
                    .keySet()
                    .forEach(category -> categoryLayoutVisibilityForShops.put(category, false));
        }

        mainViewSearchContextForUserSessionAttribute.setShopForWhichCategoryLayoutIsShown(StringUtils.EMPTY);
        mainViewSearchRequestSessionAttribute.getShopsWithCategories().clear();
    }
}
