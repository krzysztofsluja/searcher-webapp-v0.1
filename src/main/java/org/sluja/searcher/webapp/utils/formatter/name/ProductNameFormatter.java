package org.sluja.searcher.webapp.utils.formatter.name;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Element;

public class ProductNameFormatter {

    private static final String INCORRECT_NAME_ELEMENT = "'";

    public static String format(final Element element) {
        return element.text().replaceAll(INCORRECT_NAME_ELEMENT, StringUtils.EMPTY);
    }
}
