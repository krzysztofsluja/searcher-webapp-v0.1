package org.sluja.searcher.webapp.service.product.get.implementation.manyshops;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.sluja.searcher.webapp.annotation.log.object.ObjectMethodEndLog;
import org.sluja.searcher.webapp.annotation.log.object.ObjectMethodStartLog;
import org.sluja.searcher.webapp.annotation.validation.InputValidation;
import org.sluja.searcher.webapp.builder.request.product.get.GetProductForShopAndCategoryRequestBuilder;
import org.sluja.searcher.webapp.dto.product.ProductDTO;
import org.sluja.searcher.webapp.dto.product.request.get.GetProductForManyShopsAndCategoriesRequest;
import org.sluja.searcher.webapp.dto.product.request.get.GetProductForShopNameAndCategoryRequest;
import org.sluja.searcher.webapp.dto.product.request.get.GetProductForShopNameRequest;
import org.sluja.searcher.webapp.dto.product.response.GetProductForShopAndCategoryResponse;
import org.sluja.searcher.webapp.dto.product.response.GetProductsForShopAndManyCategoriesResponse;
import org.sluja.searcher.webapp.exception.ExceptionWithErrorCodeAndMessage;
import org.sluja.searcher.webapp.exception.product.general.ProductNotFoundException;
import org.sluja.searcher.webapp.exception.product.object.ProductForShopAndCategoryNotFoundException;
import org.sluja.searcher.webapp.exception.product.request.BadGetProductRequestException;
import org.sluja.searcher.webapp.service.product.get.IGetProductService;
import org.sluja.searcher.webapp.utils.logger.LoggerMessageUtils;
import org.sluja.searcher.webapp.utils.logger.LoggerUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import oshi.util.tuples.Pair;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
@Qualifier("getProductForManyShopsAndCategoriesService")
@RequiredArgsConstructor
@Slf4j
public class GetProductForManyShopsAndCategoriesService implements IGetProductService<List<GetProductsForShopAndManyCategoriesResponse>, GetProductForManyShopsAndCategoriesRequest> {

    private final IGetProductService<GetProductForShopAndCategoryResponse, GetProductForShopNameAndCategoryRequest> getProductForShopAndCategoryService;
    private final LoggerMessageUtils loggerMessageUtils;
    @Override
    @InputValidation(inputs = {GetProductForManyShopsAndCategoriesRequest.class})
    @ObjectMethodStartLog
    @ObjectMethodEndLog
    public List<GetProductsForShopAndManyCategoriesResponse> get(final GetProductForManyShopsAndCategoriesRequest request) throws ProductNotFoundException {
        final List<CompletableFuture<GetProductsForShopAndManyCategoriesResponse>> futureResponses = new ArrayList<>();
        for(final String shop : request.getShopsWithCategories().keySet()) {
            try {
                futureResponses.add(getResponseFuture(shop, request.getShopsPropertiesMap().get(shop)));
            } catch (final RuntimeException e) {
                continue;
            }
        }
        CompletableFuture.allOf(futureResponses.toArray(new CompletableFuture[0])).join();
        return futureResponses.stream()
                .map(future -> {
                    try {
                        return future.get();
                    } catch (final ExecutionException | InterruptedException e) {
                        log.error(loggerMessageUtils.getErrorLogMessageWithDeclaredErrorMessage(LoggerUtils.getCurrentClassName(),
                                LoggerUtils.getCurrentMethodName(),
                                e.getMessage()));
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .toList();
    }

    @InputValidation(inputs = {GetProductForShopNameRequest.class})
    private CompletableFuture<GetProductsForShopAndManyCategoriesResponse> getResponseFuture(final String shop, final GetProductForShopNameRequest request) {
        return CompletableFuture.supplyAsync(() -> {
            if(Objects.isNull(request)) {
                try {
                    throw new BadGetProductRequestException(BadGetProductRequestException.Type.REQUEST_NULL);
                } catch (final BadGetProductRequestException e) {
                    log.error(loggerMessageUtils.getErrorLogMessage(LoggerUtils.getCurrentClassName(),
                            LoggerUtils.getCurrentMethodName(),
                            e.getMessageCode(),
                            e.getErrorCode()));
                    throw new RuntimeException(e);
                }
            }
           final Map<String, List<ProductDTO>> productsForShop = new HashMap<>();
           for(final String category : request.getCategories()) {
               final GetProductForShopNameAndCategoryRequest requestForCategory = GetProductForShopAndCategoryRequestBuilder.build(request, shop, category);
               final List<ProductDTO> products;
               try {
                   products = getProductForShopAndCategoryService.get(requestForCategory).products();
                   if(CollectionUtils.isEmpty(products)) {
                       throw new ProductForShopAndCategoryNotFoundException(new Pair<>(shop, category));
                   }
                   productsForShop.put(category, products);
               } catch (final ProductNotFoundException e) {
                   log.error(loggerMessageUtils.getErrorLogMessage(LoggerUtils.getCurrentClassName(),
                           LoggerUtils.getCurrentMethodName(),
                           e.getMessageCode(),
                           e.getErrorCode()));
                   continue;
               }
           }
           return GetProductsForShopAndManyCategoriesResponse.builder()
                   .shopName(shop)
                   .categories(request.getCategories())
                   .productsForCategory(productsForShop)
                   .build();
        }).handleAsync((r,e) -> {
            if(Objects.nonNull(e)) {
                if(e instanceof ExceptionWithErrorCodeAndMessage ex) {
                    log.error(loggerMessageUtils.getErrorLogMessage(LoggerUtils.getCurrentClassName(),
                            LoggerUtils.getCurrentMethodName(),
                            ex.getMessageCode(),
                            ex.getErrorCode()));
                } else {
                    log.error(loggerMessageUtils.getErrorLogMessageWithDeclaredErrorMessage(LoggerUtils.getCurrentClassName(),
                            LoggerUtils.getCurrentMethodName(),
                            e.getMessage()));
                }
                return GetProductsForShopAndManyCategoriesResponse.empty(shop, request.getCategories());
            }
            return r;
        });
    }
}
