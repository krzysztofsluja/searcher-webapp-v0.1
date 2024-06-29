package org.sluja.searcher.webapp.utils.formatter.price;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Element;
import org.sluja.searcher.webapp.dto.product.request.search.object.BuildProductObjectRequest;

public class ProductPriceFormatter {

    private static final String INCORRECT_PRICE_SEPARATOR = ",";
    private static final String CORRECT_PRICE_SEPARATOR = ".";
    private static final String REGEX = "[^\\d.]";

    public static String format(final BuildProductObjectRequest request, final Element element) {
        String price = StringUtils.EMPTY;
        try {
            return element.text()
                    .replaceAll(INCORRECT_PRICE_SEPARATOR, CORRECT_PRICE_SEPARATOR)
                    .replaceAll(REGEX, StringUtils.EMPTY);
        } catch (NumberFormatException ex) {
            price = element.select(request.getProductDiscountPrice())
                    .text()
                    .replaceAll(REGEX, StringUtils.EMPTY);
            if(StringUtils.isEmpty(price)) {
                String content = request.getProductPrice();
                price = element.attr(content);
            }
        }
        return price;
    }
}
