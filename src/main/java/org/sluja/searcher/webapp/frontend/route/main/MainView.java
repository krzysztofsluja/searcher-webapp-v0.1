package org.sluja.searcher.webapp.frontend.route.main;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.router.PreserveOnRefresh;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.apache.commons.lang3.StringUtils;
import org.sluja.searcher.webapp.dto.context.ContextDto;
import org.sluja.searcher.webapp.dto.product.view.route.SearchProductsForShopsAndCategoriesRouteViewRequest;
import org.sluja.searcher.webapp.dto.session.MainViewSearchProductsSessionAttribute;
import org.sluja.searcher.webapp.dto.session.MainViewSearchRequestSessionAttribute;
import org.sluja.searcher.webapp.frontend.components.main.category.ICategoryLayout;
import org.sluja.searcher.webapp.frontend.components.main.category.options.ISearchCategoryOptionsLayout;
import org.sluja.searcher.webapp.frontend.components.main.category.options.SearchCategoryOptionsLayout;
import org.sluja.searcher.webapp.frontend.components.main.context.IContextLayout;
import org.sluja.searcher.webapp.frontend.components.main.shop.IShopLayout;
import org.sluja.searcher.webapp.service.presentation.category.GetCategoryService;
import org.sluja.searcher.webapp.service.presentation.shop.list.GetShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.Objects;

@Route("/public/main")
@AnonymousAllowed
@RouteAlias("/main")
@PreserveOnRefresh
public class MainView extends VerticalLayout {

    private final GetShopService getShopService;
    private final GetCategoryService getCategoryService;
    private final IShopLayout shopLayout;
    private final ICategoryLayout categoryLayout;
    private final ISearchCategoryOptionsLayout searchCategoryOptionsLayout;
    private final SearchProductsForShopsAndCategoriesRouteViewRequest searchProductsForShopsAndCategoriesRouteViewRequest;
    private final MainViewSearchProductsSessionAttribute mainViewSearchProductsSessionAttribute;
    private final MainViewSearchRequestSessionAttribute mainViewSearchRequestSessionAttribute;
    private final IContextLayout mainViewContextLayout;
    private final HorizontalLayout shopsWithCategoriesLayout;

    @Autowired
    public MainView(final GetShopService getShopService,
                    final GetCategoryService getCategoryService,
                    final SearchProductsForShopsAndCategoriesRouteViewRequest searchProductsForShopsAndCategoriesRouteViewRequest,
                    final IShopLayout shopLayout,
                    final ICategoryLayout categoryLayout,
                    final ISearchCategoryOptionsLayout searchCategoryOptionsLayout,
                    final MainViewSearchProductsSessionAttribute mainViewSearchProductsSessionAttribute,
                    @Qualifier("mainViewContextLayout") final IContextLayout mainViewContextLayout,
                    final MainViewSearchRequestSessionAttribute mainViewSearchRequestSessionAttribute) {
        this.shopLayout = shopLayout;
        this.categoryLayout = categoryLayout;
        this.getShopService = getShopService;
        this.getCategoryService = getCategoryService;
        this.searchCategoryOptionsLayout = searchCategoryOptionsLayout;
        this.mainViewSearchProductsSessionAttribute = mainViewSearchProductsSessionAttribute;
        this.searchProductsForShopsAndCategoriesRouteViewRequest = searchProductsForShopsAndCategoriesRouteViewRequest;
        this.mainViewSearchRequestSessionAttribute = mainViewSearchRequestSessionAttribute;
        this.mainViewContextLayout = mainViewContextLayout;
        mainViewSearchProductsSessionAttribute.getSelectedShopNames().clear();
        mainViewSearchProductsSessionAttribute.getOtherShopForEachCategoryCategoryLayoutShowed().clear();
        mainViewSearchProductsSessionAttribute.setActuallyClickedCategoryShopButton(StringUtils.EMPTY);
        mainViewSearchRequestSessionAttribute.setContext(StringUtils.EMPTY);
        mainViewSearchProductsSessionAttribute.setContext(StringUtils.EMPTY);
        mainViewSearchRequestSessionAttribute.getShopsWithCategories().clear();
        shopsWithCategoriesLayout = getShopsWithCategoriesLayout();
        mainViewSearchProductsSessionAttribute.setMainView(this);
        setupView();
     }

    private FlexLayout getMainLayout() {
        final FlexLayout mainLayout = new FlexLayout();
        mainLayout.setFlexWrap(FlexLayout.FlexWrap.WRAP);
        mainLayout.setJustifyContentMode(FlexLayout.JustifyContentMode.START);
        mainLayout.add(searchCategoryOptionsLayout.getLayout());
        mainLayout.add(mainViewContextLayout.buildContextLayout());
        mainLayout.add(shopsWithCategoriesLayout);
        mainLayout.add(getButtonLayout());
        return mainLayout;
    }

    public void setupView() {
        removeAll();
        addContextChangeListener();
        addSearchOptionsListener();
        add(getMainLayout());
    }

    public void refreshShopsWithCategoriesLayout() {
        shopsWithCategoriesLayout.removeAll();
        shopsWithCategoriesLayout.add(getShopsWithCategoriesLayout());
    }

    private HorizontalLayout getShopsWithCategoriesLayout() {
        final HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.add(shopLayout.getShopLayout());
        horizontalLayout.add(categoryLayout.getCategoriesLayout());
        return horizontalLayout;
    }

    private void addContextChangeListener() {
        final ComboBox<ContextDto> contextComboBox = (ComboBox<ContextDto>) mainViewContextLayout.getMainComponent();
        contextComboBox.addValueChangeListener(event -> {
            if(Objects.nonNull(event.getValue())) {
                String newContext = event.getValue().name();
                mainViewSearchProductsSessionAttribute.setContext(newContext);
                mainViewSearchRequestSessionAttribute.setContext(newContext);
                refreshShopsWithCategoriesLayout();
            }
        });
    }

    private void addSearchOptionsListener() {
        final RadioButtonGroup<String> radioButtonGroup = (RadioButtonGroup<String>) searchCategoryOptionsLayout.getMainComponent();
        radioButtonGroup.addValueChangeListener(event -> {
            if(Objects.nonNull(event.getValue())) {

                final boolean option = event.getValue().equalsIgnoreCase(((SearchCategoryOptionsLayout)searchCategoryOptionsLayout).getOTHER_CATEGORY_FOR_SHOPS());
                mainViewSearchProductsSessionAttribute.setOtherCategoryForEachShop(option);
                mainViewSearchProductsSessionAttribute.setOtherCategoryLayoutVisibilityForEachShop();
                mainViewSearchProductsSessionAttribute.setActuallyClickedCategoryShopButton(StringUtils.EMPTY);
                mainViewSearchRequestSessionAttribute.getShopsWithCategories().clear();
                refreshShopsWithCategoriesLayout();
            }
        });
    }

    private HorizontalLayout getButtonLayout() {
        final HorizontalLayout buttonLayout = new HorizontalLayout();
        final Button searchButton = new Button("SZUKAJ");
        searchButton.addClickListener(event -> {
            searchProductsForShopsAndCategoriesRouteViewRequest.setContext(mainViewSearchRequestSessionAttribute.getContext());
            searchProductsForShopsAndCategoriesRouteViewRequest.setShopsWithCategories(mainViewSearchRequestSessionAttribute.getShopsWithCategories());
            searchButton.getUI().ifPresent(ui -> ui.navigate("/public/products"));
        });
        buttonLayout.add(searchButton);
        return buttonLayout;
    }
}
