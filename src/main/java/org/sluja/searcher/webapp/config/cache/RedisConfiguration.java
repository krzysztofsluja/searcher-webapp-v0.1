package org.sluja.searcher.webapp.config.cache;

import org.sluja.searcher.webapp.dto.product.cache.ProductsForShopAndCategoryRedisObject;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class RedisConfiguration {

    @Bean
    public RedisTemplate<String, ProductsForShopAndCategoryRedisObject> productsForShopAndCategoryRedisTemplate(final RedisConnectionFactory connectionFactory) {
        final RedisTemplate<String, ProductsForShopAndCategoryRedisObject> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        return template;
    }
}
