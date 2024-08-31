package org.sluja.searcher.webapp.dto.product.cart;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.sluja.searcher.webapp.utils.logger.LoggerMessageUtils;
import org.sluja.searcher.webapp.utils.logger.LoggerUtils;
import org.sluja.searcher.webapp.utils.message.builder.InformationMessageBuilder;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@NoArgsConstructor
@Getter
@Slf4j
public class CartDTO {

    private final Map<String, CartProductDTO> products = new HashMap<>();
    @Setter
    private String userIp = StringUtils.EMPTY;
    @Autowired
    private LoggerMessageUtils loggerMessageUtils;

    private boolean doesCartContainProduct(final String id) {
        return products.keySet().stream().anyMatch(productKey -> productKey.equals(id));
    }

    public void changeQuantity(final String id, final Long quantity) {
        final CartProductDTO productDTO = products.entrySet().stream()
                .filter(productKey -> productKey.getKey().equals(id))
                .findAny()
                .map(Map.Entry::getValue)
                .orElseThrow();
        productDTO.setQuantity(productDTO.getQuantity() + quantity);
        if(productDTO.getQuantity() <= 0) {
            deleteCartProduct(id);
            return;
        }
        products.put(id, productDTO);
    }

    private void increaseQuantity(final String id, final Long quantity) {
        if(quantity > 0) {
            changeQuantity(id, quantity);
        }
    }

    public void deleteCartProduct(final String id) {
        products.remove(id);
    }

    public void addProduct(final CartProductDTO product) {
        if(doesCartContainProduct(product.getId())) {
            log.info(loggerMessageUtils.getInfoLogMessage(LoggerUtils.getCurrentClassName(),
                    LoggerUtils.getCurrentMethodName(),
                    InformationMessageBuilder.buildParametrizedMessage("info.cart.addition.product.already.in.cart", List.of(product.toString()))));
            increaseQuantity(product.getId(), 1L);
            return;
        }
        products.putIfAbsent(product.getId(), product);
    }

    public void clearCart() {
        products.clear();
    }
}
