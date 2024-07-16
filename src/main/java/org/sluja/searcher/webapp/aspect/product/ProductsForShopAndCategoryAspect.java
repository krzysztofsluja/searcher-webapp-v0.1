package org.sluja.searcher.webapp.aspect.product;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.sluja.searcher.webapp.builder.cache.product.ProductsForShopAndCategoryRedisObjectBuilder;
import org.sluja.searcher.webapp.dto.product.cache.ProductsForShopAndCategoryRedisObject;
import org.sluja.searcher.webapp.dto.product.request.get.GetProductForShopNameAndCategoryRequest;
import org.sluja.searcher.webapp.dto.product.response.GetProductForShopAndCategoryResponse;
import org.sluja.searcher.webapp.exception.ExceptionWithErrorCodeAndMessage;
import org.sluja.searcher.webapp.exception.cache.CacheElementForGivenKeyNotFound;
import org.sluja.searcher.webapp.exception.cache.CacheKeyCreationFailedException;
import org.sluja.searcher.webapp.service.cache.CacheService;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class ProductsForShopAndCategoryAspect {

    private final CacheService<ProductsForShopAndCategoryRedisObject> getProductsForShopAndCategoryCacheService;
    private final ProductsForShopAndCategoryRedisObjectBuilder productsForShopAndCategoryRedisObjectBuilder;

    @Pointcut("execution(* org.sluja.searcher.webapp.service.product.get.implementation.singleshop.GetProductForShopAndCategoryService.get(..)) && args(request)")
    public void productForShopAndCategoryPointcut(final GetProductForShopNameAndCategoryRequest request) {}

    @AfterReturning(pointcut = "productForShopAndCategoryPointcut(request)", returning = "response")
    public void saveProducts(final GetProductForShopNameAndCategoryRequest request, final GetProductForShopAndCategoryResponse response) {
        //TODO logging
        final ProductsForShopAndCategoryRedisObject redisObject;
        try {
            redisObject = productsForShopAndCategoryRedisObjectBuilder.build(response);
            getProductsForShopAndCategoryCacheService.save(redisObject);
        } catch (CacheKeyCreationFailedException e) {
            //TODO logging
        }
    }

    @Around("productForShopAndCategoryPointcut(request)")
    public GetProductForShopAndCategoryResponse getProductsCache(final ProceedingJoinPoint joinPoint, final GetProductForShopNameAndCategoryRequest request) throws ExceptionWithErrorCodeAndMessage {
        try {
            final ProductsForShopAndCategoryRedisObject redisObject = productsForShopAndCategoryRedisObjectBuilder.buildObjectWithEmptyProductsList(request);
            ProductsForShopAndCategoryRedisObject cachedProducts = getProductsForShopAndCategoryCacheService.get(redisObject.id());
            return GetProductForShopAndCategoryResponse.builder()
                    .shopName(cachedProducts.shopName())
                    .categoryName(cachedProducts.category())
                    .products(cachedProducts.products())
                    .build();
        } catch (CacheElementForGivenKeyNotFound | CacheKeyCreationFailedException e) {
            try {
                return  (GetProductForShopAndCategoryResponse) joinPoint.proceed();
            } catch (Throwable ex) {
                //TODO logging
                throw new ExceptionWithErrorCodeAndMessage(ExceptionWithErrorCodeAndMessage.GENERAL_MESSAGE_CODE, ExceptionWithErrorCodeAndMessage.GENERAL_ERROR_CODE);
            }
        }
    }
}
