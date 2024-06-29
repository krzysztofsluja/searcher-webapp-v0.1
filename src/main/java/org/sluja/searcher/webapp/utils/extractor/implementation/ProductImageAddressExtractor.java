package org.sluja.searcher.webapp.utils.extractor.implementation;


import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.jboss.logging.Logger;
import org.jsoup.nodes.Element;
import org.sluja.searcher.webapp.dto.product.ProductDTO;
import org.sluja.searcher.webapp.dto.product.request.search.object.BuildProductObjectRequest;
import org.sluja.searcher.webapp.dto.scraper.stat.StaticWebsiteElementScrapRequest;
import org.sluja.searcher.webapp.exception.format.UnsuccessfulFormatException;
import org.sluja.searcher.webapp.exception.product.general.ProductNotFoundException;
import org.sluja.searcher.webapp.exception.scraper.ScraperIncorrectFieldException;
import org.sluja.searcher.webapp.service.scraper.interfaces.WebsiteScraper;
import org.sluja.searcher.webapp.utils.extractor.Extractor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
@Qualifier("productImageAddressExtractor")
@RequiredArgsConstructor
public class ProductImageAddressExtractor implements Extractor<List<String>, Element, BuildProductObjectRequest> {

    private static final Logger LOGGER = Logger.getLogger(ProductImageAddressExtractor.class);
    private final WebsiteScraper<Element, StaticWebsiteElementScrapRequest> staticWebsiteElementScraper;

    @Override
    public List<String> extract(final Element element, final BuildProductObjectRequest request) throws ProductNotFoundException, UnsuccessfulFormatException {

        final List<Element> elements = request.getProductImageAddresses()
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
        final List<String> imageAddresses = elements.stream()
                .map(e -> e.attr(request.getProductImageExtractAttribute()))
                .toList();
        return CollectionUtils.isEmpty(imageAddresses) ? ProductDTO.NO_IMAGE_ADDRESS() : imageAddresses;
    }
}
