package org.sluja.searcher.webapp.service.presentation.product;

import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.sluja.searcher.webapp.annotation.log.object.ObjectMethodEndLog;
import org.sluja.searcher.webapp.annotation.log.object.ObjectMethodStartLog;
import org.sluja.searcher.webapp.annotation.validation.InputValidation;
import org.sluja.searcher.webapp.builder.request.product.get.GetProductForManyShopsAndCategoriesRequestBuilder;
import org.sluja.searcher.webapp.dto.presentation.category.property.CategoryPropertyDto;
import org.sluja.searcher.webapp.dto.presentation.shop.attribute.ShopAttributeDto;
import org.sluja.searcher.webapp.dto.product.request.get.GetProductForManyShopsAndCategoriesRequest;
import org.sluja.searcher.webapp.dto.product.response.GetProductsForShopAndManyCategoriesResponse;
import org.sluja.searcher.webapp.dto.request.presentation.product.GetProductsRequest;
import org.sluja.searcher.webapp.exception.presentation.SpecificEntityNotFoundException;
import org.sluja.searcher.webapp.exception.presentation.shop.attribute.AttributeForGivenShopInContextNotFoundException;
import org.sluja.searcher.webapp.exception.product.general.ProductNotFoundException;
import org.sluja.searcher.webapp.service.presentation.category.property.GetCategoryPropertyService;
import org.sluja.searcher.webapp.service.presentation.shop.attribute.GetShopAttributesService;
import org.sluja.searcher.webapp.service.product.get.IGetProductService;
import org.sluja.searcher.webapp.utils.logger.LoggerMessageUtils;
import org.sluja.searcher.webapp.utils.logger.LoggerUtils;
import org.sluja.searcher.webapp.utils.message.builder.InformationMessageBuilder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class GetProductsService implements IGetProductService<List<GetProductsForShopAndManyCategoriesResponse>, GetProductsRequest> {

    private final Validator validator;
    private final GetCategoryPropertyService getCategoryPropertyService;
    private final GetShopAttributesService getShopAttributesService;
    private final IGetProductService<List<GetProductsForShopAndManyCategoriesResponse>, GetProductForManyShopsAndCategoriesRequest> getProductForManyShopsAndCategoriesService;
    private final LoggerMessageUtils loggerMessageUtils;

    @Override
    @InputValidation(inputs = {GetProductsRequest.class})
    @ObjectMethodStartLog
    @ObjectMethodEndLog
    public List<GetProductsForShopAndManyCategoriesResponse> get(final GetProductsRequest request) throws ProductNotFoundException {
        final Map<String, List<String>> categoryPropertyDtos = getCategoriesProperties(request);
        try {
            //TODO logging
            final Map<String, List<ShopAttributeDto>> shopAttributeDtos = getAttributesForShops(request);
            final GetProductForManyShopsAndCategoriesRequest getProductForManyShopsAndCategoriesRequest = GetProductForManyShopsAndCategoriesRequestBuilder.build(request, shopAttributeDtos, categoryPropertyDtos);
            return getProductForManyShopsAndCategoriesService.get(getProductForManyShopsAndCategoriesRequest);
        } catch (final SpecificEntityNotFoundException e) {
            log.error(loggerMessageUtils.getErrorLogMessage(LoggerUtils.getCurrentClassName(),
                    LoggerUtils.getCurrentMethodName(),
                    e.getMessage(),
                    e.getErrorCode()));
            return request.shopsNames().stream()
                    .map(shopName -> GetProductsForShopAndManyCategoriesResponse.empty(shopName, request.categories().get(shopName)))
                    .toList();
        }
    }

    @InputValidation(inputs = {GetProductsRequest.class})
    private Map<String, List<String>> getCategoriesProperties(final GetProductsRequest request) {
        final List<String> allCategories = request.categories()
                .values()
                .stream()
                .flatMap(List::stream)
                .distinct()
                .toList();
        final List<CategoryPropertyDto> categoryPropertyDtos = getCategoryPropertyService.findPropertiesForCategories(allCategories, request.context());
        final Map<String, List<String>> properties = categoryPropertyDtos.stream()
                    .filter(property -> property.getContext().equalsIgnoreCase(request.context()))
                    .collect(Collectors.groupingBy(CategoryPropertyDto::getCategoryName, Collectors.mapping(CategoryPropertyDto::getValue, Collectors.toList())));
        if(properties.keySet().size() != allCategories.size()) {
            //TODO logging
            allCategories.stream()
                    .filter(category -> !properties.containsKey(category))
                    .forEach(category -> properties.put(category, Collections.emptyList()));
        }
        return properties;
    }

    @InputValidation(inputs = {GetProductsRequest.class})
    private Map<String, List<ShopAttributeDto>> getAttributesForShops(final GetProductsRequest request) throws SpecificEntityNotFoundException {
        final Map<String, List<ShopAttributeDto>> attributesForManyShops = getShopAttributesService.getAttributesForManyShopsInContext(request.shopsNames(), request.context());
        if (CollectionUtils.isEmpty(attributesForManyShops.values())) {
            throw new AttributeForGivenShopInContextNotFoundException();
        }
        if(attributesForManyShops.keySet().size() != request.shopsNames().size()) {
            getShopsInContextWithoutFoundAttributes(attributesForManyShops, request).forEach(
                shop -> log.info(loggerMessageUtils.getInfoLogMessage(LoggerUtils.getCurrentClassName(), LoggerUtils.getCurrentMethodName(), InformationMessageBuilder.buildParametrizedMessage("info.attribute.not.found.for.part.of.request", List.of(shop.getLeft(), shop.getRight())))));
        }
        return attributesForManyShops;
    }

    private List<Pair<String, String>> getShopsInContextWithoutFoundAttributes(final Map<String, List<ShopAttributeDto>> attributesForShops, final GetProductsRequest request) {
        return request.shopsNames()
                .stream()
                .filter(shop -> !attributesForShops.containsKey(shop))
                .filter(Objects::nonNull)
                .map(shop -> Pair.of(shop, request.context()))
                .toList();
    }
}
