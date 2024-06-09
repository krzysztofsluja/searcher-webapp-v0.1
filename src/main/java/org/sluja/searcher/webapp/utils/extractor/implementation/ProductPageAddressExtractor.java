package org.sluja.searcher.webapp.utils.extractor.implementation;

import lombok.RequiredArgsConstructor;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Element;
import org.sluja.searcher.webapp.dto.scraper.ProductScrapWithDefinedAttributes;
import org.sluja.searcher.webapp.enums.product.ProductProperty;
import org.sluja.searcher.webapp.exception.format.UnsuccessfulFormatException;
import org.sluja.searcher.webapp.exception.product.ProductNotFoundException;
import org.sluja.searcher.webapp.service.scraper.product.ProductScraper;
import org.sluja.searcher.webapp.utils.extractor.Extractor;
import org.sluja.searcher.webapp.utils.formatter.ProductFormatter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Qualifier("productPageAddressExtractor")
@RequiredArgsConstructor
public class ProductPageAddressExtractor implements Extractor<List<String>, Element, ProductScrapWithDefinedAttributes> {

    private final ProductScraper productScraperService;
    @Override
    public List<String> extract(final Element element, final ProductScrapWithDefinedAttributes request) throws UnsuccessfulFormatException, ProductNotFoundException {
        List<Element> elements =  productScraperService.scrapElementsByAttributes(element, request.productPageAddresses());
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
