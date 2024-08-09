package org.sluja.searcher.webapp.frontend.product;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import lombok.Setter;
import org.sluja.searcher.webapp.dto.product.ProductDTO;
import org.sluja.searcher.webapp.dto.product.response.GetProductsForShopAndManyCategoriesResponse;
import org.sluja.searcher.webapp.dto.request.presentation.product.GetProductsRequest;
import org.sluja.searcher.webapp.exception.product.general.ProductNotFoundException;
import org.sluja.searcher.webapp.service.product.get.IGetProductService;
import org.sluja.searcher.webapp.utils.message.MessageReader;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

@Route("/public/products")
@CssImport("./styles/product-view.css")
@AnonymousAllowed
public class ProductsForShopsAndCategoryView extends VerticalLayout {

    private final int PRODUCTS_PER_ROW = 6;
    private final IGetProductService<List<GetProductsForShopAndManyCategoriesResponse>, GetProductsRequest> getProductsService;
    private final MessageReader informationMessageReader;
    @Setter
    private GetProductsRequest request;

    public ProductsForShopsAndCategoryView(@Autowired IGetProductService<List<GetProductsForShopAndManyCategoriesResponse>, GetProductsRequest> getProductsService,
                                            @Autowired MessageReader informationMessageReader) {
        this.getProductsService = getProductsService;
        this.informationMessageReader = informationMessageReader;
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
        add(getMainLayout());
    }

    private FlexLayout getMainLayout() {
        final FlexLayout productLayout = new FlexLayout();
        productLayout.setFlexWrap(FlexLayout.FlexWrap.WRAP);
        productLayout.setJustifyContentMode(FlexLayout.JustifyContentMode.START);
        productLayout.addClassName("product-layout");

        request = new GetProductsRequest(List.of("bmxlife"), Map.of("bmxlife", List.of("bars")), Map.of("bmxlife", false), "skate");
        final List<GetProductsForShopAndManyCategoriesResponse> products;
        try {
            products = getProductsService.get(request);
        } catch (ProductNotFoundException e) {
            throw new RuntimeException(e);
        }
        //FOR TESTS
        final List<ProductDTO> productsForCategory = products.getFirst().getProductsForCategory().get(products.getFirst().getCategories().getFirst());
        productsForCategory.forEach(product -> productLayout.add(getProductCard(product)));
        return productLayout;
    }

    private VerticalLayout getProductCard(final ProductDTO product) {
        final VerticalLayout productCard = new VerticalLayout();
        productCard.addClassName("product-card");

        final Image image = new Image();
        image.setSrc(product.imageProductPageAddress().getFirst());
        image.setAlt("Product Image");
        image.addClassName("product-image");

        final Div nameLabel = new Div(new Text(product.name()));
        final Div priceLabel = new Div(new Text(product.price().toPlainString()));
        final Div storeLabel = new Div(new Text(product.shopName()));

        productCard.add(image, nameLabel, priceLabel, storeLabel);
        return productCard;
    }
}