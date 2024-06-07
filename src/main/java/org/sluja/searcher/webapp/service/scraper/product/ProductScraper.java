package org.sluja.searcher.webapp.service.scraper.product;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.sluja.searcher.webapp.dto.product.ProductScrapWithDefinedAttributes;
import org.sluja.searcher.webapp.service.scraper.Scraper;

import java.util.List;

public interface ProductScraper extends Scraper<Elements, ProductScrapWithDefinedAttributes> {

    default Element scrapElementByAttribute(final Element page, final String attribute) {
        return page.selectFirst(attribute);
    };

    default List<Element> scrapElementsByAttributes(final Element page, final List<String> attributes) {
        return attributes.stream()
                .map(page::selectFirst)
                .toList();
    }
}
