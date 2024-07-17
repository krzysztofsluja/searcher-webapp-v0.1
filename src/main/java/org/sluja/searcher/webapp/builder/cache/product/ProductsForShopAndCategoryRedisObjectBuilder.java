package org.sluja.searcher.webapp.builder.cache.product;

import lombok.RequiredArgsConstructor;
import org.sluja.searcher.webapp.dto.creator.product.ProductsForShopAndCategoryKeyCreatorData;
import org.sluja.searcher.webapp.dto.product.cache.ProductsForShopAndCategoryRedisObject;
import org.sluja.searcher.webapp.dto.product.request.get.GetProductForShopNameAndCategoryRequest;
import org.sluja.searcher.webapp.dto.product.response.GetProductForShopAndCategoryResponse;
import org.sluja.searcher.webapp.exception.cache.CacheKeyCreationFailedException;
import org.sluja.searcher.webapp.utils.creator.Creator;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Collections;

@Component
@RequiredArgsConstructor
public class ProductsForShopAndCategoryRedisObjectBuilder {

    private final Creator<String, ProductsForShopAndCategoryKeyCreatorData> productsForShopAndCategoryCacheKeyCreator;

    public ProductsForShopAndCategoryRedisObject build(final GetProductForShopAndCategoryResponse response) throws CacheKeyCreationFailedException {
        return new ProductsForShopAndCategoryRedisObject(
                productsForShopAndCategoryCacheKeyCreator.create(new ProductsForShopAndCategoryKeyCreatorData(response.shopName(), response.categoryName(), LocalDate.now())),
                response.shopName(),
                response.categoryName(),
                LocalDate.now(),
                response.products());
    }

    public ProductsForShopAndCategoryRedisObject buildObjectWithEmptyProductsList(final GetProductForShopNameAndCategoryRequest request) throws CacheKeyCreationFailedException {
        return new ProductsForShopAndCategoryRedisObject(
                productsForShopAndCategoryCacheKeyCreator.create(new ProductsForShopAndCategoryKeyCreatorData(request.getShopName(), request.getCategoryName(), LocalDate.now())),
                request.getShopName(),
                request.getCategoryName(),
                LocalDate.now(),
                Collections.emptyList());
    }
}
