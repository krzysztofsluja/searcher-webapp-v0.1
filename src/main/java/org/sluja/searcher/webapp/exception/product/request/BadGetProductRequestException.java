package org.sluja.searcher.webapp.exception.product.request;

import lombok.Getter;
import org.sluja.searcher.webapp.exception.ExceptionWithErrorCodeAndMessage;

public class BadGetProductRequestException extends ExceptionWithErrorCodeAndMessage {

    public BadGetProductRequestException(final Type type) {
        super(type.getErrorMessage(), 3008L);
    }

    @Getter
    public enum Type {
        EMPTY_SHOPS_LIST("error.product.request.empty.shops.list"),
        SHOP_AMOUNT_EXCEEDED("error.product.request.too.much.shops.selected"),
        SHOP_CATEGORY_LIST_EMPTY("error.product.request.category.list.empty"),
        CATEGORY_FOR_SHOP_AMOUNT_EXCEEDED("error.product.request.category.too.much.for.shop.selected");

        final String errorMessage;
        Type(final String errorMessage) {
            this.errorMessage = errorMessage;
        }
    }


}