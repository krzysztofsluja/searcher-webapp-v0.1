package org.sluja.searcher.webapp.service.presentation.product;

import lombok.RequiredArgsConstructor;
import org.sluja.searcher.webapp.builder.request.product.get.GetProductForManyShopsAndCategoriesRequestBuilder;
import org.sluja.searcher.webapp.dto.presentation.category.property.CategoryPropertyDto;
import org.sluja.searcher.webapp.dto.presentation.shop.attribute.ShopAttributeDto;
import org.sluja.searcher.webapp.dto.product.request.get.GetProductForManyShopsAndCategoriesRequest;
import org.sluja.searcher.webapp.dto.product.response.GetProductsForShopAndManyCategoriesResponse;
import org.sluja.searcher.webapp.dto.request.presentation.product.GetProductsRequest;
import org.sluja.searcher.webapp.exception.presentation.SpecificEntityNotFoundException;
import org.sluja.searcher.webapp.exception.product.general.ProductNotFoundException;
import org.sluja.searcher.webapp.service.presentation.category.property.GetCategoryPropertyService;
import org.sluja.searcher.webapp.service.presentation.shop.attribute.GetShopAttributesService;
import org.sluja.searcher.webapp.service.product.get.IGetProductService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GetProductsService implements IGetProductService<List<GetProductsForShopAndManyCategoriesResponse>, GetProductsRequest> {

    private final GetCategoryPropertyService getCategoryPropertyService;
    private final GetShopAttributesService getShopAttributesService;
    private final IGetProductService<List<GetProductsForShopAndManyCategoriesResponse>, GetProductForManyShopsAndCategoriesRequest> getProductForManyShopsAndCategoriesService;
    @Override
    public List<GetProductsForShopAndManyCategoriesResponse> get(final GetProductsRequest request) throws ProductNotFoundException {
        //TODO logging
        final Map<String, List<String>> categoryPropertyDtos = getCategoriesProperties(request);
        try {
            //TODO logging + validation
            final Map<String, List<ShopAttributeDto>> shopAttributeDtos = getAttributesForShops(request);
            final GetProductForManyShopsAndCategoriesRequest getProductForManyShopsAndCategoriesRequest = GetProductForManyShopsAndCategoriesRequestBuilder.build(request, shopAttributeDtos, categoryPropertyDtos);
            return getProductForManyShopsAndCategoriesService.get(getProductForManyShopsAndCategoriesRequest);
        } catch (SpecificEntityNotFoundException e) {
            //TODO return empty list of products
        }
        return null;
    }

    private Map<String, List<String>> getCategoriesProperties(final GetProductsRequest request) {
        final List<String> allCategories = request.categories()
                .values()
                .stream()
                .flatMap(List::stream)
                .distinct()
                .toList();
        try {
           final List<CategoryPropertyDto> categoryPropertyDtos = getCategoryPropertyService.findPropertiesForCategories(allCategories, request.context());
           return categoryPropertyDtos.stream()
                   .filter(property -> property.getContext().equalsIgnoreCase(request.context()))
                   .collect(Collectors.groupingBy(CategoryPropertyDto::getCategoryName, Collectors.mapping(CategoryPropertyDto::getValue, Collectors.toList())));
        } catch (final SpecificEntityNotFoundException e) {
            //TODO logging
            return Collections.emptyMap();
        }
    }

    private Map<String, List<ShopAttributeDto>> getAttributesForShops(final GetProductsRequest request) throws SpecificEntityNotFoundException {
        return getShopAttributesService.getAttributesForManyShopsInContext(request.shopsNames(), request.context());
    }


}
