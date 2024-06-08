package org.sluja.searcher.webapp.utils.extractor.implementation;


import lombok.RequiredArgsConstructor;
import org.jboss.logging.Logger;
import org.jsoup.nodes.Element;

import org.sluja.searcher.webapp.dto.scraper.ProductScrapWithDefinedAttributes;
import org.sluja.searcher.webapp.exception.format.UnsuccessfulFormatException;
import org.sluja.searcher.webapp.exception.product.ProductNotFoundException;
import org.sluja.searcher.webapp.service.scraper.product.ProductScraper;
import org.sluja.searcher.webapp.utils.extractor.Extractor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;


import java.util.List;

@Component
@Qualifier("productImageAddressExtractor")
@RequiredArgsConstructor
public class ProductImageAddressExtractor implements Extractor<List<String>, Element, ProductScrapWithDefinedAttributes> {

    private static final Logger LOGGER = Logger.getLogger(ProductImageAddressExtractor.class);
    private final ProductScraper productScraperService;

    @Override
    public List<String> extract(final Element element, final ProductScrapWithDefinedAttributes request) throws ProductNotFoundException, UnsuccessfulFormatException {
        List<Element> elements = productScraperService.scrapElementsByAttributes(element, request.productImageAddresses());
        List<String> imageAddresses = elements.stream()
                .map(e -> e.attr(request.productImageExtractAttribute()))
                .toList();
        if (imageAddresses.isEmpty()) {
            LOGGER.info("Product doesn't have any image! Default image will be used");
            //TODO add default image
            //return ProductScrapWithDefinedAttributes.NO_IMAGE_ADDRESS();
        }
        return imageAddresses;
    }
}
