package org.sluja.searcher.webapp.utils.formatter.price;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Element;

public class ProductPriceFormatter {

    private static final String INCORRECT_PRICE_SEPARATOR = ",";
    private static final String CORRECT_PRICE_SEPARATOR = ".";
    public static final String REGEX = "[^\\d.]";

    public static String format(final Element element) {
        return element.text()
                .replaceAll(INCORRECT_PRICE_SEPARATOR, CORRECT_PRICE_SEPARATOR)
                .replaceAll(REGEX, StringUtils.EMPTY);
    }
}
