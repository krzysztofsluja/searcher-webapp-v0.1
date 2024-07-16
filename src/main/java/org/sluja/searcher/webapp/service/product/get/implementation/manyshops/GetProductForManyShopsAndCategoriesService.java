package org.sluja.searcher.webapp.service.product.get.implementation.manyshops;

import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.sluja.searcher.webapp.builder.request.product.get.GetProductForShopAndCategoryRequestBuilder;
import org.sluja.searcher.webapp.dto.product.ProductDTO;
import org.sluja.searcher.webapp.dto.product.request.get.GetProductForManyShopsAndCategoriesRequest;
import org.sluja.searcher.webapp.dto.product.request.get.GetProductForShopNameAndCategoryRequest;
import org.sluja.searcher.webapp.dto.product.request.get.GetProductForShopNameRequest;
import org.sluja.searcher.webapp.dto.product.response.GetProductForShopAndCategoryResponse;
import org.sluja.searcher.webapp.dto.product.response.GetProductsForShopAndManyCategoriesResponse;
import org.sluja.searcher.webapp.exception.product.general.ProductNotFoundException;
import org.sluja.searcher.webapp.exception.product.object.ProductForShopAndCategoryNotFoundException;
import org.sluja.searcher.webapp.service.product.get.IGetProductService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import oshi.util.tuples.Pair;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
@Qualifier("getProductForManyShopsAndCategoriesService")
@RequiredArgsConstructor
public class GetProductForManyShopsAndCategoriesService implements IGetProductService<List<GetProductsForShopAndManyCategoriesResponse>, GetProductForManyShopsAndCategoriesRequest> {

    private final IGetProductService<GetProductForShopAndCategoryResponse, GetProductForShopNameAndCategoryRequest> getProductForShopAndCategoryService;
    @Override
    public List<GetProductsForShopAndManyCategoriesResponse> get(final GetProductForManyShopsAndCategoriesRequest request) throws ProductNotFoundException {
        final List<CompletableFuture<GetProductsForShopAndManyCategoriesResponse>> futureResponses = new ArrayList<>();
        for(final String shop : request.getShopsWithCategories().keySet()) {
            futureResponses.add(getResponseFuture(shop, request.getShopsPropertiesMap().get(shop)));
        }
        CompletableFuture.allOf(futureResponses.toArray(new CompletableFuture[0])).join();
        return futureResponses.stream()
                .map(future -> {
                    try {
                        return future.get();
                    } catch (final ExecutionException | InterruptedException e) {
                        //TODO logging
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .toList();
    }

    private CompletableFuture<GetProductsForShopAndManyCategoriesResponse> getResponseFuture(final String shop, final GetProductForShopNameRequest request) {
        return CompletableFuture.supplyAsync(() -> {
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
               } catch (final ProductNotFoundException | ProductForShopAndCategoryNotFoundException e) {
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
                //TODO logging
                return GetProductsForShopAndManyCategoriesResponse.empty();
            }
            return r;
        });
    }
}
