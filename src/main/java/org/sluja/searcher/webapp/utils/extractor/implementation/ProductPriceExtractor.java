package org.sluja.searcher.webapp.utils.extractor.implementation;

import lombok.RequiredArgsConstructor;
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

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
@Qualifier("productImageAddressExtractor")
@RequiredArgsConstructor
public class ProductPriceExtractor implements Extractor<BigDecimal, Element, ProductScrapWithDefinedAttributes> {

    private final ProductScraper productScraperService;
    private static final int RESOLUTION_PRICE = 2;
    @Override
    public BigDecimal extract(final Element element, final ProductScrapWithDefinedAttributes request) throws UnsuccessfulFormatException, ProductNotFoundException {
        try {
            final String noFormattedPrice = formatPrice(request, productScraperService.scrapElementByAttribute(element, request.productPrice()));
            return new BigDecimal(noFormattedPrice).setScale(RESOLUTION_PRICE, RoundingMode.HALF_UP);
        } catch (NumberFormatException ex) {
            final String discountPrice = formatPrice(request, productScraperService.scrapElementByAttribute(element, request.productDiscountPrice()));
            return new BigDecimal(discountPrice).setScale(RESOLUTION_PRICE, RoundingMode.HALF_UP);
        }
    }

    private String formatPrice(final ProductScrapWithDefinedAttributes request, Element element) throws UnsuccessfulFormatException{
        return ProductFormatter.format(request, ProductProperty.PRICE, element);
    }
}