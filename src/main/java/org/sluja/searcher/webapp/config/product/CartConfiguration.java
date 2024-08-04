package org.sluja.searcher.webapp.config.product;

import org.sluja.searcher.webapp.dto.product.cart.CartDTO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.SessionScope;

@Configuration
public class CartConfiguration {

    @Bean
    @SessionScope
    public CartDTO cartDTO() {
        return new CartDTO();
    }
}
