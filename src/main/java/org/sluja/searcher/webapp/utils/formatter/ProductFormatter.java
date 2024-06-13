package org.sluja.searcher.webapp.utils.formatter;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Element;
import org.openqa.selenium.WebElement;
import org.sluja.searcher.webapp.dto.scraper.ProductScrapWithDefinedAttributes;
import org.sluja.searcher.webapp.enums.product.ProductProperty;
import org.sluja.searcher.webapp.exception.format.UnsuccessfulFormatException;

public class ProductFormatter {

    private static final String REGEX = "[^\\d.]";
    private static final String INCORRECT_PRICE_SEPARATOR = ",";
    private static final String CORRECT_PRICE_SEPARATOR = ".";
    public static String format(final ProductScrapWithDefinedAttributes request, final ProductProperty property, final WebElement element) throws UnsuccessfulFormatException {
        return switch (property) {
            case PRICE -> formatPrice(request, element);
            case URL -> formatUrl(request, element);
            case NAME -> formatName(element);
            default -> throw new UnsuccessfulFormatException();
        };
    }

    private static String formatName(final WebElement value) {
        return value.getText().replaceAll("'", StringUtils.EMPTY);
    }

    private static String formatPrice(final ProductScrapWithDefinedAttributes request, final WebElement element) {
        String price = StringUtils.EMPTY;
        try {
            return element.getAttribute(request.productPrice())
                    .replaceAll(INCORRECT_PRICE_SEPARATOR, CORRECT_PRICE_SEPARATOR)
                    .replaceAll(REGEX, StringUtils.EMPTY);
        } catch (NumberFormatException ex) {
            price = element.getAttribute(request.productDiscountPrice())
                    .replaceAll(REGEX, StringUtils.EMPTY);
            if(StringUtils.isEmpty(price)) {
                price = element.getAttribute(request.productPrice());
            }
        }
        return price;
    }

    private static String formatUrl(final ProductScrapWithDefinedAttributes request, final WebElement value) throws NullPointerException{
        StringBuilder builder = new StringBuilder();
        final String address = value.getAttribute(request.productPageAddressAttribute());
        if(StringUtils.isEmpty(address)) {
            throw new NullPointerException();
        }
        builder.append(formatIncompleteUriForBuilder(address, request));
        builder.append(address);
        return builder.toString();
    }

    public static String formatIncompleteUri(final String uri, final ProductScrapWithDefinedAttributes request) {
        if(!uri.startsWith("http")) {
            return request.plainPageAddressToFormat() + uri;
        }
        return uri;
    }

    private static String formatIncompleteUriForBuilder(final String uri, final ProductScrapWithDefinedAttributes request) {
        final String formattedUri = formatIncompleteUri(uri, request);
        return uri.equalsIgnoreCase(formattedUri) ? StringUtils.EMPTY : formattedUri;
    }
}
