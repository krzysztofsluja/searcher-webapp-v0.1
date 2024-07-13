package org.sluja.searcher.webapp.exception.format;

import org.sluja.searcher.webapp.enums.product.ProductProperty;
import org.sluja.searcher.webapp.exception.ExceptionWithErrorCodeAndMessage;

public class UnsuccessfulFormatException extends ExceptionWithErrorCodeAndMessage {

    public UnsuccessfulFormatException(final ProductProperty property) {
        super(getMessageCode(property), 4001L);
    }

    private static String getMessageCode(final ProductProperty property) {
        return switch(property) {
            case PRICE -> "error.product.unsuccessful.format.price";
            case URL -> "error.product.unsuccessful.format.url";
            case NAME -> "error.product.unsuccessful.format.name";
            default -> ExceptionWithErrorCodeAndMessage.GENERAL_MESSAGE_CODE;
        };
    }
}
