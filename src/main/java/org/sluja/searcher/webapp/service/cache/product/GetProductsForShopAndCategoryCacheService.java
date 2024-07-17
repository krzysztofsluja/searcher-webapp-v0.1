package org.sluja.searcher.webapp.service.cache.product;

import lombok.RequiredArgsConstructor;
import org.sluja.searcher.webapp.dto.product.cache.ProductsForShopAndCategoryRedisObject;
import org.sluja.searcher.webapp.exception.cache.CacheElementForGivenKeyNotFound;
import org.sluja.searcher.webapp.service.cache.CacheService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Qualifier("getProductsForShopAndCategoryCacheService")
public class GetProductsForShopAndCategoryCacheService implements CacheService<ProductsForShopAndCategoryRedisObject> {

    private final RedisTemplate<String, ProductsForShopAndCategoryRedisObject> productsForShopAndCategoryRedisTemplate;
    @Override
    public void save(final ProductsForShopAndCategoryRedisObject object) {
        //TODO logging
        final String key = object.id();
        if (notExists(key)) {
            productsForShopAndCategoryRedisTemplate.opsForValue().set(key, object);
        }
    }

    @Override
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
    public void clearCache() {
        //TODO logging
        Objects.requireNonNull(productsForShopAndCategoryRedisTemplate.getConnectionFactory()).getConnection().serverCommands().flushAll();
    }

    private Boolean notExists(final String key) {
        return !exists(key);
    }
}