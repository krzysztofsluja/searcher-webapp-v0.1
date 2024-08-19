package org.sluja.searcher.webapp.service.frontend.view.mainview;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.sluja.searcher.webapp.dto.product.view.route.SearchProductsForShopsAndCategoriesRouteViewRequest;
import org.sluja.searcher.webapp.dto.session.MainViewSearchRequestSessionAttribute;
import org.sluja.searcher.webapp.dto.session.frontend.mainview.MainViewSearchContextForUserSessionAttribute;
import org.sluja.searcher.webapp.frontend.route.main.MainView;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class MainViewService {

    private final MainViewSearchContextForUserSessionAttribute mainViewSearchContextForUserSessionAttribute;
    private final MainViewSearchRequestSessionAttribute mainViewSearchRequestSessionAttribute;
    private final SearchProductsForShopsAndCategoriesRouteViewRequest searchProductsForShopsAndCategoriesRouteViewRequest;

    public void refreshShopsWithCategoriesLayout() {
        if(Objects.nonNull(mainViewSearchContextForUserSessionAttribute.getMainView())) {
            mainViewSearchContextForUserSessionAttribute.getMainView().refreshShopsWithCategoriesLayout();
        }
    }

    public void initContext(final MainView mainView) {
        mainViewSearchContextForUserSessionAttribute.getSelectedShopsNames().clear();
        mainViewSearchContextForUserSessionAttribute.getCategoryLayoutVisibilityForShop().clear();
        mainViewSearchContextForUserSessionAttribute.setShopForWhichCategoryLayoutIsShown(StringUtils.EMPTY);
        mainViewSearchRequestSessionAttribute.setContext(StringUtils.EMPTY);
        mainViewSearchContextForUserSessionAttribute.setCategoryContext(StringUtils.EMPTY);
        mainViewSearchRequestSessionAttribute.getShopsWithCategories().clear();
        mainViewSearchContextForUserSessionAttribute.setMainView(mainView);
    }

    public void setProductViewSearchRequest() {
        searchProductsForShopsAndCategoriesRouteViewRequest.setContext(mainViewSearchContextForUserSessionAttribute.getCategoryContext());
        searchProductsForShopsAndCategoriesRouteViewRequest.setShopsWithCategories(mainViewSearchRequestSessionAttribute.getShopsWithCategories());
    }

}
