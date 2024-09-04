package org.sluja.searcher.webapp.frontend.route.main;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PreserveOnRefresh;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.sluja.searcher.webapp.frontend.components.main.category.ICategoryLayout;
import org.sluja.searcher.webapp.frontend.components.main.category.options.ISearchCategoryOptionsLayout;
import org.sluja.searcher.webapp.frontend.components.main.contact.IContactFormLayout;
import org.sluja.searcher.webapp.frontend.components.main.context.IContextLayout;
import org.sluja.searcher.webapp.frontend.components.main.shop.IShopLayout;
import org.sluja.searcher.webapp.service.frontend.view.mainview.MainViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

@Route("/public/main")
@AnonymousAllowed
@RouteAlias("/main")
@PreserveOnRefresh
public class MainView extends VerticalLayout {

    private final IShopLayout shopLayout;
    private final ICategoryLayout categoryLayout;
    private final ISearchCategoryOptionsLayout searchCategoryOptionsLayout;
    private final IContextLayout mainViewContextLayout;
    private final HorizontalLayout shopsWithCategoriesLayout;
    private final MainViewService mainViewService;
    private final IContactFormLayout mainViewContactFormLayout;
    private final VerticalLayout contactFormLayout;

    @Autowired
    public MainView(final IShopLayout shopLayout,
                    final ICategoryLayout categoryLayout,
                    final ISearchCategoryOptionsLayout searchCategoryOptionsLayout,
                    @Qualifier("mainViewContextLayout") final IContextLayout mainViewContextLayout,
                    final MainViewService mainViewService,
                    @Qualifier("mainViewContactFormLayout") final IContactFormLayout mainViewContactFormLayout) {
        this.shopLayout = shopLayout;
        this.categoryLayout = categoryLayout;
        this.searchCategoryOptionsLayout = searchCategoryOptionsLayout;
        this.mainViewContextLayout = mainViewContextLayout;
        this.mainViewService = mainViewService;
        this.mainViewContactFormLayout = mainViewContactFormLayout;
        this.mainViewService.initContext(this);
        shopsWithCategoriesLayout = getShopsWithCategoriesLayout();
        contactFormLayout = mainViewContactFormLayout.build();
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
        mainLayout.add(contactFormLayout);
        return mainLayout;
    }

    public void setupView() {
        removeAll();
        add(getMainLayout());
    }

    public void refreshShopsWithCategoriesLayout() {
        shopsWithCategoriesLayout.removeAll();
        shopsWithCategoriesLayout.add(getShopsWithCategoriesLayout());
    }

    public void refreshContactFormLayout() {
        contactFormLayout.removeAll();
        contactFormLayout.add(mainViewContactFormLayout.build());
    }

    private HorizontalLayout getShopsWithCategoriesLayout() {
        final HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.add(shopLayout.getShopLayout());
        horizontalLayout.add(categoryLayout.getCategoriesLayout());
        return horizontalLayout;
    }

    private HorizontalLayout getButtonLayout() {
        final HorizontalLayout buttonLayout = new HorizontalLayout();
        final Button searchButton = new Button("SZUKAJ");
        searchButton.addClickListener(_ -> {
            mainViewService.setProductViewSearchRequest();
            searchButton.getUI().ifPresent(ui -> ui.navigate("/public/products"));
        });
        buttonLayout.add(searchButton);
        return buttonLayout;
    }
}
