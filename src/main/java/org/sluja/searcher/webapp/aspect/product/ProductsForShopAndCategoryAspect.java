package org.sluja.searcher.webapp.aspect.product;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.sluja.searcher.webapp.annotation.log.object.ObjectMethodStartLog;
import org.sluja.searcher.webapp.builder.cache.product.ProductsForShopAndCategoryRedisObjectBuilder;
import org.sluja.searcher.webapp.dto.product.cache.ProductsForShopAndCategoryRedisObject;
import org.sluja.searcher.webapp.dto.product.request.get.GetProductForShopNameAndCategoryRequest;
import org.sluja.searcher.webapp.dto.product.response.GetProductForShopAndCategoryResponse;
import org.sluja.searcher.webapp.exception.ExceptionWithErrorCodeAndMessage;
import org.sluja.searcher.webapp.exception.cache.CacheElementForGivenKeyNotFound;
import org.sluja.searcher.webapp.exception.cache.CacheKeyCreationFailedException;
import org.sluja.searcher.webapp.service.cache.CacheService;
import org.sluja.searcher.webapp.utils.logger.LoggerMessageUtils;
import org.sluja.searcher.webapp.utils.logger.LoggerUtils;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class ProductsForShopAndCategoryAspect {

    private final CacheService<ProductsForShopAndCategoryRedisObject> getProductsForShopAndCategoryCacheService;
    private final ProductsForShopAndCategoryRedisObjectBuilder productsForShopAndCategoryRedisObjectBuilder;
    private final LoggerMessageUtils loggerMessageUtils;

    @Pointcut("execution(* org.sluja.searcher.webapp.service.product.get.implementation.singleshop.GetProductForShopAndCategoryService.get(..)) && args(request)")
    public void productForShopAndCategoryPointcut(final GetProductForShopNameAndCategoryRequest request) {}

    @AfterReturning(pointcut = "productForShopAndCategoryPointcut(request)", returning = "response")
    @ObjectMethodStartLog
    public void saveProducts(final GetProductForShopNameAndCategoryRequest request, final GetProductForShopAndCategoryResponse response) {
        final ProductsForShopAndCategoryRedisObject redisObject;
        try {
            redisObject = productsForShopAndCategoryRedisObjectBuilder.build(response);
            getProductsForShopAndCategoryCacheService.save(redisObject);
        } catch (final CacheKeyCreationFailedException e) {
            log.error(loggerMessageUtils.getErrorLogMessage(LoggerUtils.getCurrentClassName(),
                    LoggerUtils.getCurrentMethodName(),
                    e.getMessageCode(),
                    e.getErrorCode()), e);
        } catch (final RedisConnectionFailureException e) {
            log.error(loggerMessageUtils.getErrorLogMessageWithDeclaredErrorMessage(LoggerUtils.getCurrentClassName(),
                    LoggerUtils.getCurrentMethodName(),
                    e.getMessage()), e);
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
                    .context(request.getContext())
                    .build();
        } catch (CacheElementForGivenKeyNotFound | CacheKeyCreationFailedException | RedisConnectionFailureException e) {
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
            try {
                return  (GetProductForShopAndCategoryResponse) joinPoint.proceed();
            } catch (Throwable ex) {
                //TODO logging
                //TODO error handling
                throw new ExceptionWithErrorCodeAndMessage(ExceptionWithErrorCodeAndMessage.GENERAL_MESSAGE_CODE, ExceptionWithErrorCodeAndMessage.GENERAL_ERROR_CODE);
            }
        }
    }
}
