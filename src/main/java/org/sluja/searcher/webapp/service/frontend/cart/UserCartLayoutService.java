package org.sluja.searcher.webapp.service.frontend.cart;

import lombok.RequiredArgsConstructor;
import org.sluja.searcher.webapp.dto.cart.UserCartProductDto;
import org.sluja.searcher.webapp.dto.cart.UserCartProductsForShopDto;
import org.sluja.searcher.webapp.dto.session.frontend.cart.UserCartSessionAttribute;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserCartLayoutService {

    private final UserCartSessionAttribute userCartSessionAttribute;

    public List<UserCartProductsForShopDto> getSummaryForShops() {
        final Map<String, List<UserCartProductDto>> productsForShops = getProductsForShops();
        if (productsForShops.isEmpty()) {
            return Collections.emptyList();
        }
        return productsForShops.keySet()
                .stream()
                .map(shop -> UserCartProductsForShopDto.builder()
                        .shopName(shop)
                        .products(productsForShops.get(shop))
                        .productsQuantity(getProductsQuantity(productsForShops.get(shop)))
                        .productsPrice(getProductsPrice(productsForShops.get(shop)))
                        .build())
                .toList();
    }

    public BigDecimal getTotalPrice() {
        return getSummaryForShops().stream()
                .map(UserCartProductsForShopDto::getProductsPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal getProductsPrice(final List<UserCartProductDto> shopCartProducts) {
        return shopCartProducts.stream()
                .map(product -> product.getProduct().price().multiply(BigDecimal.valueOf(product.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private Long getProductsQuantity(final List<UserCartProductDto> shopCartProducts) {
        return shopCartProducts.stream()
                .mapToLong(UserCartProductDto::getQuantity)
                .sum();
    }

    public Map<String, List<UserCartProductDto>> getProductsForShops() {
        final List<UserCartProductDto> cartProducts = userCartSessionAttribute.getCartProducts();
        return cartProducts.stream()
                .collect(Collectors.groupingBy(product -> product.getProduct().shopName()));
    }

    public Long getCartItemsAmount() {
        return userCartSessionAttribute.getCartProducts().stream()
                .mapToLong(UserCartProductDto::getQuantity)
                .sum();
    }

    public String getSumPriceForShop(final List<UserCartProductDto> products) {
        return String.valueOf(getProductsPrice(products));
    }
}
