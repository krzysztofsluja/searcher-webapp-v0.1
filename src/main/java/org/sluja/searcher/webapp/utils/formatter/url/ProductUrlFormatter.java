package org.sluja.searcher.webapp.utils.formatter.url;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Element;
import org.sluja.searcher.webapp.dto.product.request.search.object.BuildProductObjectRequest;
import org.sluja.searcher.webapp.enums.product.ProductProperty;
import org.sluja.searcher.webapp.exception.format.UnsuccessfulFormatException;

public class ProductUrlFormatter {

    public static String format(final BuildProductObjectRequest request, final Element value) throws UnsuccessfulFormatException {
        StringBuilder builder = new StringBuilder();
        final String address = value.attr(request.getPageAddressExtractAttribute());
        if(StringUtils.isEmpty(address)) {
            throw new UnsuccessfulFormatException(ProductProperty.URL);
        }
        builder.append(formatIncompleteUriForBuilder(address, request));
        builder.append(address);
        return builder.toString();
    }

    public static String formatIncompleteUri(final String url, final BuildProductObjectRequest request) {
        if(StringUtils.isEmpty(request.getPlainPageAddressToFormat())) {
            return url;
        }
        if(!url.startsWith("http")) {
            return request.getPlainPageAddressToFormat() + url;
        }
        return url;
    }

    private static String formatIncompleteUriForBuilder(final String uri, final BuildProductObjectRequest request) {
        final String formattedUri = formatIncompleteUri(uri, request);
        return uri.equalsIgnoreCase(formattedUri) ? StringUtils.EMPTY : formattedUri;
    }

}
