package org.sluja.searcher.webapp.utils.extractor.implementation;

import lombok.RequiredArgsConstructor;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Element;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.sluja.searcher.webapp.dto.scraper.ProductScrapWithDefinedAttributes;
import org.sluja.searcher.webapp.enums.product.ProductProperty;
import org.sluja.searcher.webapp.exception.format.UnsuccessfulFormatException;
import org.sluja.searcher.webapp.exception.product.ProductNotFoundException;
import org.sluja.searcher.webapp.utils.extractor.Extractor;
import org.sluja.searcher.webapp.utils.formatter.ProductFormatter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Qualifier("productPageAddressExtractor")
@RequiredArgsConstructor
public class ProductPageAddressExtractor implements Extractor<List<String>, WebElement, ProductScrapWithDefinedAttributes> {

    @Override
    public List<String> extract(final WebElement element, final ProductScrapWithDefinedAttributes request) throws UnsuccessfulFormatException, ProductNotFoundException {
      /*//  List<Element> elements =  productScraperService.scrapElementsByAttributes(element, request.productPageAddresses());
        List<WebElement> elements1 = element.findElements(By.tagName()request.productPageAddresses());
        return elements.stream()
                .map(elem -> {
                    try {
                        return ProductFormatter.format(request, ProductProperty.URL, elem);
                    } catch (UnsuccessfulFormatException e) {
                        return StringUtils.EMPTY;
                    }
                })
                .filter(StringUtils::isNotEmpty)
                .toList();*/
        return null;
    }
}
