package org.sluja.searcher.webapp.utils.formatter;

import org.jsoup.nodes.Element;
import org.sluja.searcher.webapp.dto.product.request.search.object.BuildProductObjectRequest;
import org.sluja.searcher.webapp.enums.product.ProductProperty;
import org.sluja.searcher.webapp.exception.format.UnsuccessfulFormatException;
import org.sluja.searcher.webapp.utils.formatter.name.ProductNameFormatter;
import org.sluja.searcher.webapp.utils.formatter.price.ProductPriceFormatter;
import org.sluja.searcher.webapp.utils.formatter.url.ProductUrlFormatter;

public class ProductFormatter {

    public static String format(final BuildProductObjectRequest request, final ProductProperty property, final Element element) throws UnsuccessfulFormatException {
        return switch (property) {
            case PRICE -> ProductPriceFormatter.format(request, element);
            case URL -> ProductUrlFormatter.format(request, element);
            case NAME -> ProductNameFormatter.format(element);
            default -> throw new UnsuccessfulFormatException();
        };
    }
}
