package org.sluja.searcher.webapp.builder.request.product.get;

import jakarta.validation.Valid;
import org.sluja.searcher.webapp.builder.request.product.ProductBuilder;
import org.sluja.searcher.webapp.dto.presentation.shop.attribute.ShopAttributeDto;
import org.sluja.searcher.webapp.dto.product.request.get.GetProductForManyShopsAndCategoriesRequest;
import org.sluja.searcher.webapp.dto.product.request.get.GetProductForShopNameRequest;
import org.sluja.searcher.webapp.dto.request.presentation.product.GetProductsRequest;
import org.sluja.searcher.webapp.dto.request.search.manyshops.ManyShopsSearchProductsRequest;
import org.sluja.searcher.webapp.dto.scraper.ProductScrapWithDefinedAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class GetProductForManyShopsAndCategoriesRequestBuilder extends ProductBuilder {

    public static @Valid GetProductForManyShopsAndCategoriesRequest build(final ManyShopsSearchProductsRequest request) {
        return GetProductForManyShopsAndCategoriesRequest.builder()
                .shopsWithCategories(getShopsWithCategories(request))
                .shopsPropertiesMap(getShopsPropertiesMap(request.getShopsProperties()))
                .build();
    }

    public static @Valid GetProductForManyShopsAndCategoriesRequest build(final GetProductsRequest request, final Map<String, List<ShopAttributeDto>> shopAttributes, final Map<String, List<String>> categoryProperties) {
        final Map<String, GetProductForShopNameRequest> shopsPropertiesMap = new HashMap<>();
        for(final Map.Entry<String, List<ShopAttributeDto>> entry : shopAttributes.entrySet()) {
            GetProductForShopNameRequest requestForShop = GetProductForShopNameRequestBuilder.build(entry.getValue(),
                    request.dynamicWebsitesOfShops().get(entry.getKey()),
                    categoryProperties,
                    request.categories().get(entry.getKey()));
            shopsPropertiesMap.put(entry.getKey(), requestForShop);
        }
        return GetProductForManyShopsAndCategoriesRequest.builder()
                .shopsWithCategories(request.categories())
                .shopsPropertiesMap(shopsPropertiesMap)
                .context(request.context())
                .build();
    }

    private static Map<String,List<String>> getShopsWithCategories(final ManyShopsSearchProductsRequest request) {
        return request.getShopsProperties().stream()
                .collect(Collectors.toMap(ProductScrapWithDefinedAttributes::shopName, ProductScrapWithDefinedAttributes::categories));
    }

    private static Map<String, GetProductForShopNameRequest> getShopsPropertiesMap(final List<ProductScrapWithDefinedAttributes> shopsProperties) {
        return shopsProperties.stream()
                .map(GetProductForShopNameRequestBuilder::build)
                .collect(Collectors.toMap(GetProductForShopNameRequest::getShopName, Function.identity()));
    }

}
