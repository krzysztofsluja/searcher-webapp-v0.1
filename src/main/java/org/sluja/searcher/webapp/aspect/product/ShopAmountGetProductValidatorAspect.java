package org.sluja.searcher.webapp.aspect.product;

import jakarta.validation.Valid;
import org.apache.commons.collections4.CollectionUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.sluja.searcher.webapp.dto.product.request.get.GetProductForManyShopsAndCategoriesRequest;
import org.sluja.searcher.webapp.exception.product.request.BadGetProductRequestException;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Aspect
@Component
public class ShopAmountGetProductValidatorAspect {

    private final int SHOP_AMOUNT_THRESHOLD = 3;
    private final int CATEGORY_FOR_SHOP_THRESHOLD = 4;

    @Before("execution(* org.sluja.searcher.webapp.service.product.get.implementation.manyshops.*.*(..))")
    public void validateRequest(final JoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            if (arg instanceof GetProductForManyShopsAndCategoriesRequest request) {
                final Set<String> shops = request.getShopsWithCategories().keySet();
                if (Objects.isNull(shops)|| CollectionUtils.isEmpty(shops)) {
                    throw new BadGetProductRequestException(BadGetProductRequestException.Type.EMPTY_SHOPS_LIST);
                }
                if(shops.size() > SHOP_AMOUNT_THRESHOLD) {
                    throw new BadGetProductRequestException(BadGetProductRequestException.Type.SHOP_AMOUNT_EXCEEDED);
                }
                final boolean categoryAmountExceeded = request.getShopsWithCategories().entrySet()
                        .stream()
                        .anyMatch(entry -> entry.getValue().size() > CATEGORY_FOR_SHOP_THRESHOLD);
                if(categoryAmountExceeded) {
                    throw new BadGetProductRequestException(BadGetProductRequestException.Type.CATEGORY_FOR_SHOP_AMOUNT_EXCEEDED);
                }
                final int shopsAmount = request.getShopsWithCategories().entrySet().stream()
                        .filter(e -> CollectionUtils.isEmpty(e.getValue()))
                        .map(Map.Entry::getKey)
                        .collect(Collectors.toSet())
                        .size();
                if(shopsAmount > 1) {
                    throw new BadGetProductRequestException(BadGetProductRequestException.Type.SHOP_CATEGORY_LIST_EMPTY);
                }
            }
        }
    }
}
