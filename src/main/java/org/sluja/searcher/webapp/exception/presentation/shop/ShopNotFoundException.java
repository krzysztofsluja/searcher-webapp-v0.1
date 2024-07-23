package org.sluja.searcher.webapp.exception.presentation.shop;

import org.sluja.searcher.webapp.exception.presentation.SpecificEntityNotFoundException;

public class ShopNotFoundException extends SpecificEntityNotFoundException {

    public ShopNotFoundException() {
        super("error.entity.shop.not.found", 8001L);
    }
}
