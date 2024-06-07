package org.sluja.searcher.webapp.service.scraper.product;

import lombok.RequiredArgsConstructor;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import org.sluja.searcher.webapp.dto.scraper.ProductScrapWithDefinedAttributes;
import org.sluja.searcher.webapp.exception.connection.ConnectionTimeoutException;
import org.sluja.searcher.webapp.service.scraper.Scraper;
import org.sluja.searcher.webapp.service.scraper.connector.ScraperConnector;
import org.sluja.searcher.webapp.utils.connector.Connector;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Qualifier("productScraperService")
public class ProductScraperService implements ProductScraper {

    private final Connector<Document> scraperConnector;

    @Override
    public Elements scrapElements(final ProductScrapWithDefinedAttributes attribute, final List<String> addresses) throws ConnectionTimeoutException, IOException {
        final String div = attribute.div();
        final String productInstance = attribute.productInstance();
        final Iterator<String> iterator = addresses.iterator();
        final Elements elements = scrapElementsFromPage(iterator.next(), div).select(productInstance);
        while(iterator.hasNext()) {
            elements.addAll(scrapElementsFromPage(iterator.next(), div).select(productInstance));
        }
        final Set<Element> distinctElementsSet = new HashSet<>(elements);
        return new Elements(distinctElementsSet);
    }

    @Override
    public Elements scrapElementsFromPage(final String pageUrl, final String attributeName) throws ConnectionTimeoutException, IOException {
        return scraperConnector.connectAndGetPage(pageUrl).select(attributeName);
    }

    @Override
    public String scrapElementFromPageBySpecificAttribute(String pageUrl, String selectAttributeName, String attributeName) throws ConnectionTimeoutException, IOException {
        return scrapElementsFromPage(pageUrl, selectAttributeName).attr(attributeName);
    }
}
