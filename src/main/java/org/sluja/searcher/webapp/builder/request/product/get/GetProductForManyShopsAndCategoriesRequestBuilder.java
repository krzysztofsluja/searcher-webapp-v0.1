package org.sluja.searcher.webapp.builder.request.product.get;

import org.sluja.searcher.webapp.builder.request.product.ProductBuilder;
import org.sluja.searcher.webapp.dto.product.request.get.GetProductForManyShopsAndCategoriesRequest;
import org.sluja.searcher.webapp.dto.product.request.get.GetProductForShopNameRequest;
import org.sluja.searcher.webapp.dto.request.search.manyshops.ManyShopsSearchProductsRequest;
import org.sluja.searcher.webapp.dto.scraper.ProductScrapWithDefinedAttributes;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class GetProductForManyShopsAndCategoriesRequestBuilder extends ProductBuilder {

    public static GetProductForManyShopsAndCategoriesRequest build(final ManyShopsSearchProductsRequest request) {
        return GetProductForManyShopsAndCategoriesRequest.builder()
                .shopsWithCategories(getShopsWithCategories(request))
                .shopsPropertiesMap(getShopsPropertiesMap(request.getShopsProperties()))
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
