package org.sluja.searcher.webapp.utils.extractor.implementation;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Element;
import org.sluja.searcher.webapp.dto.product.request.search.object.BuildProductObjectRequest;
import org.sluja.searcher.webapp.dto.scraper.stat.StaticWebsiteElementScrapRequest;
import org.sluja.searcher.webapp.enums.product.ProductProperty;
import org.sluja.searcher.webapp.exception.format.UnsuccessfulFormatException;
import org.sluja.searcher.webapp.exception.product.general.ProductNotFoundException;
import org.sluja.searcher.webapp.exception.scraper.ScraperIncorrectFieldException;
import org.sluja.searcher.webapp.service.scraper.interfaces.WebsiteScraper;
import org.sluja.searcher.webapp.utils.extractor.Extractor;
import org.sluja.searcher.webapp.utils.formatter.ProductFormatter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
@Qualifier("productPageAddressExtractor")
@RequiredArgsConstructor
public class ProductPageAddressExtractor implements Extractor<List<String>, Element, BuildProductObjectRequest> {

    private final WebsiteScraper<Element, StaticWebsiteElementScrapRequest> staticWebsiteElementScraper;

    @Override
    public List<String> extract(final Element element, final BuildProductObjectRequest request) throws UnsuccessfulFormatException, ProductNotFoundException {
        final List<Element> elements = request.getProductPageAddresses()
                .stream()
                .filter(StringUtils::isNotEmpty)
                .map(address -> {
                    final StaticWebsiteElementScrapRequest scrapRequest = new StaticWebsiteElementScrapRequest(address, element);
                    try {
                        return staticWebsiteElementScraper.scrap(scrapRequest);
                    } catch (ScraperIncorrectFieldException e) {
                        //TODO logging
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .toList();
        return elements.stream()
                .map(elem -> {
                    try {
                        return ProductFormatter.format(request, ProductProperty.URL, elem);
                    } catch (UnsuccessfulFormatException e) {
                        return StringUtils.EMPTY;
                    }
                })
                .filter(StringUtils::isNotEmpty)
                .toList();
    }
}
