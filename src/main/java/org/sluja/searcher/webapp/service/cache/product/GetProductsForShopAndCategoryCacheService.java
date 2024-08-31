package org.sluja.searcher.webapp.service.cache.product;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sluja.searcher.webapp.annotation.log.noobject.MethodEndLog;
import org.sluja.searcher.webapp.annotation.log.noobject.MethodStartLog;
import org.sluja.searcher.webapp.annotation.log.object.ObjectMethodEndLog;
import org.sluja.searcher.webapp.annotation.log.object.ObjectMethodStartLog;
import org.sluja.searcher.webapp.annotation.validation.InputValidation;
import org.sluja.searcher.webapp.dto.product.cache.ProductsForShopAndCategoryRedisObject;
import org.sluja.searcher.webapp.exception.cache.CacheElementForGivenKeyNotFound;
import org.sluja.searcher.webapp.service.cache.CacheService;
import org.sluja.searcher.webapp.utils.logger.LoggerMessageUtils;
import org.sluja.searcher.webapp.utils.logger.LoggerUtils;
import org.sluja.searcher.webapp.utils.message.builder.InformationMessageBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Qualifier("getProductsForShopAndCategoryCacheService")
@Slf4j
public class GetProductsForShopAndCategoryCacheService implements CacheService<ProductsForShopAndCategoryRedisObject> {

    private final RedisTemplate<String, ProductsForShopAndCategoryRedisObject> productsForShopAndCategoryRedisTemplate;
    private final LoggerMessageUtils loggerMessageUtils;
    @Override
    @InputValidation(inputs = {ProductsForShopAndCategoryRedisObject.class})
    @ObjectMethodStartLog
    @MethodEndLog
    public void save(final ProductsForShopAndCategoryRedisObject object) {
        final String key = object.id();
        if (notExists(key)) {
            productsForShopAndCategoryRedisTemplate.opsForValue().set(key, object);
        }
    }

    @Override
    @MethodStartLog
    @ObjectMethodEndLog
    public ProductsForShopAndCategoryRedisObject get(final String key) throws CacheElementForGivenKeyNotFound {
        final ProductsForShopAndCategoryRedisObject object = productsForShopAndCategoryRedisTemplate.opsForValue().get(key);
        if(Objects.isNull(object)) {
            throw new CacheElementForGivenKeyNotFound();
        }
        return object;
    }

    @Override
    public Boolean exists(final String key) {
        return productsForShopAndCategoryRedisTemplate.hasKey(key);
    }

    @Scheduled(cron = "0 0 23 * * ?")
    @MethodStartLog
    public void clearCache() {
        log.info(loggerMessageUtils.getInfoLogMessage(LoggerUtils.getCurrentClassName(),
                LoggerUtils.getCurrentMethodName(),
                InformationMessageBuilder.buildParametrizedMessage("info.product.cache.clear", List.of(LocalDate.now().toString()))));
        Objects.requireNonNull(productsForShopAndCategoryRedisTemplate.getConnectionFactory()).getConnection().serverCommands().flushAll();
    }

    private Boolean notExists(final String key) {
        return !exists(key);
    }
}
