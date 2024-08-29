package org.sluja.searcher.webapp.frontend.route.product;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.apache.commons.lang3.StringUtils;
import org.sluja.searcher.webapp.dto.cart.UserCartProductDto;
import org.sluja.searcher.webapp.dto.product.ProductDTO;
import org.sluja.searcher.webapp.dto.product.response.GetProductsForShopAndManyCategoriesResponse;
import org.sluja.searcher.webapp.dto.product.view.route.SearchProductsForShopsAndCategoriesRouteViewRequest;
import org.sluja.searcher.webapp.dto.request.presentation.product.GetProductsRequest;
import org.sluja.searcher.webapp.exception.product.general.ProductNotFoundException;
import org.sluja.searcher.webapp.service.product.get.IGetProductService;
import org.sluja.searcher.webapp.service.user.cart.ICart;
import org.sluja.searcher.webapp.utils.message.MessageReader;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.stream.Collectors;

@Route("/public/products")
@CssImport("./styles/product-view.css")
@AnonymousAllowed
public class ProductsForShopsAndCategoryView extends VerticalLayout implements AfterNavigationObserver {

    private final int PRODUCTS_PER_ROW = 6;
    private final IGetProductService<List<GetProductsForShopAndManyCategoriesResponse>, GetProductsRequest> getProductsService;
    private final MessageReader informationMessageReader;
    private GetProductsRequest request;
    private final SearchProductsForShopsAndCategoriesRouteViewRequest searchProductsForShopsAndCategoriesRouteViewRequest;
    private final ICart<UserCartProductDto, ProductDTO> userCartService;

    public ProductsForShopsAndCategoryView(@Autowired IGetProductService<List<GetProductsForShopAndManyCategoriesResponse>, GetProductsRequest> getProductsService,
                                           @Autowired MessageReader informationMessageReader,
                                           @Autowired SearchProductsForShopsAndCategoriesRouteViewRequest searchProductsForShopsAndCategoriesRouteViewRequest,
                                           @Autowired ICart<UserCartProductDto, ProductDTO> userCartService) {
        this.getProductsService = getProductsService;
        this.informationMessageReader = informationMessageReader;
        this.searchProductsForShopsAndCategoriesRouteViewRequest = searchProductsForShopsAndCategoriesRouteViewRequest;
        this.userCartService = userCartService;
/*        GetProductsRequest request = new GetProductsRequest(List.of("bmxlife","manyfestbmx","avebmx"), Map.of("bmxlife", List.of("bars",
                "frames",
                "rims",
                "hubs"), "manyfestbmx", List.of("frames",
                "bars"), "avebmx", List.of("bars")), Map.of("bmxlife", false, "manyfestbmx", true, "avebmx", true), "skate");
        try {
            getProductsService.get(request);
        } catch (ProductNotFoundException e) {
            throw new RuntimeException(e);
        }*/
    }

    private FlexLayout getMainLayout() {
        final FlexLayout productLayout = new FlexLayout();
        productLayout.setFlexWrap(FlexLayout.FlexWrap.WRAP);
        productLayout.setJustifyContentMode(FlexLayout.JustifyContentMode.START);
        productLayout.addClassName("product-layout");

        //request = new GetProductsRequest(List.of("bmxlife"), Map.of("bmxlife", List.of("bars")), Map.of("bmxlife", false), "skate");
        final List<GetProductsForShopAndManyCategoriesResponse> products;
        try {
            products = getProductsService.get(request);
        } catch (ProductNotFoundException e) {
            throw new RuntimeException(e);
        }
        //FOR TESTS
        //final List<ProductDTO> productsForCategory = products.getFirst().getProductsForCategory().get(products.getFirst().getCategories().getFirst());
        final List<ProductDTO> productsForCategory = products.stream()
                .map(GetProductsForShopAndManyCategoriesResponse::getProductsForCategory)
                .flatMap(map -> map.values().stream()) // Stream<List<ProductDTO>>
                .flatMap(List::stream) // Stream<ProductDTO>
                .toList();

        productsForCategory.forEach(product -> productLayout.add(getProductCard(product)));
        return productLayout;
    }

    private void setRequest() {
        final Map<String, List<String>> shopsWithCategories = searchProductsForShopsAndCategoriesRouteViewRequest.getShopsWithCategories();
        if(Objects.nonNull(shopsWithCategories)) {
            this.request = new GetProductsRequest(shopsWithCategories.keySet().stream().toList(),
                    shopsWithCategories,
                    shopsWithCategories.keySet().stream().collect(Collectors.toMap(key -> key, _ -> Boolean.TRUE)),
                    searchProductsForShopsAndCategoriesRouteViewRequest.getContext()); //FOR TESTS
        }
    }

    private VerticalLayout getProductCard(final ProductDTO product) {
        final VerticalLayout productCard = new VerticalLayout();
        productCard.addClassName("product-card");

        final Image image = new Image();
        image.setSrc(getProductImageUrl(product));
        image.setAlt("Product Image");
        image.addClassName("product-image");

        final Anchor productLink = new Anchor(product.productPageAddress().getFirst(), product.name());
        productLink.setTarget("_blank");

        final Div nameLabel = new Div(productLink);
        final Div priceLabel = new Div(new Text(product.price().toPlainString()));
        final Div storeLabel = new Div(new Text(product.shopName()));

        productCard.add(image, nameLabel, priceLabel, storeLabel, getAddingToCartButton(product));
        return productCard;
    }

    private Button getAddingToCartButton(final ProductDTO product) {
        final Button button = new Button();
        button.setIcon(VaadinIcon.CART.create());
        button.addClickListener(_ -> {
            userCartService.addProductToCart(product);
        });
        return button;
    }

    private String getProductImageUrl(final ProductDTO product) {
        try {
            final String url = product.imageProductPageAddress().getFirst();
            return StringUtils.isNotEmpty(url) ? url : StringUtils.EMPTY;
        } catch (final NoSuchElementException e) {
            //TODO logging
            return StringUtils.EMPTY;
        }
    }

    @Override
    public void afterNavigation(AfterNavigationEvent afterNavigationEvent) {
        //searchProductsForShopsAndCategoriesRouteViewRequest.getShopsWithCategories();
        setRequest();
        this.add(getMainLayout());
    }
}