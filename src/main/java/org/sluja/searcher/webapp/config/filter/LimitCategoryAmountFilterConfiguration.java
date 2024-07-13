package org.sluja.searcher.webapp.config.filter;

import org.sluja.searcher.webapp.filter.product.get.LimitCategoryAmountFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class LimitCategoryAmountFilterConfiguration {

    @Bean
    public FilterRegistrationBean<LimitCategoryAmountFilter> limitCategoryAmountFilter() {
        final FilterRegistrationBean<LimitCategoryAmountFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new LimitCategoryAmountFilter());
        registration.addUrlPatterns("/testManyShops");
        return registration;
    }
}
