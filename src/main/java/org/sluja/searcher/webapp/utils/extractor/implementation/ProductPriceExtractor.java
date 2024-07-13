package org.sluja.searcher.webapp.utils.extractor.implementation;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Element;
import org.sluja.searcher.webapp.dto.product.request.search.object.BuildProductObjectRequest;
import org.sluja.searcher.webapp.dto.scraper.stat.StaticWebsiteElementScrapRequest;
import org.sluja.searcher.webapp.enums.product.ProductProperty;
import org.sluja.searcher.webapp.exception.format.UnsuccessfulFormatException;
import org.sluja.searcher.webapp.exception.scraper.ScraperIncorrectFieldException;
import org.sluja.searcher.webapp.service.factory.scraper.WebsiteScraperFactory;
import org.sluja.searcher.webapp.service.interfaces.scrap.IGetScraper;
import org.sluja.searcher.webapp.service.scraper.interfaces.WebsiteScraper;
import org.sluja.searcher.webapp.utils.extractor.Extractor;
import org.sluja.searcher.webapp.utils.formatter.ProductFormatter;
import org.sluja.searcher.webapp.utils.formatter.price.ProductPriceFormatter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
@Qualifier("productPriceExtractor")
@RequiredArgsConstructor
public class ProductPriceExtractor implements Extractor<BigDecimal, Element, BuildProductObjectRequest>, IGetScraper {

    private final WebsiteScraperFactory websiteScraperFactory;
    private static final int RESOLUTION_PRICE = 2;

    @Override
    public BigDecimal extract(final Element element, final BuildProductObjectRequest request) throws UnsuccessfulFormatException, ScraperIncorrectFieldException {
        final StaticWebsiteElementScrapRequest scrapRequest = new StaticWebsiteElementScrapRequest(request.getProductPrice(), element);
        final String noFormattedPrice = formatPrice(request, getScraperService().scrap(scrapRequest));
        try{
            return new BigDecimal(noFormattedPrice).setScale(RESOLUTION_PRICE, RoundingMode.HALF_UP);
        } catch (NumberFormatException ex) {
            String price = element.select(request.getProductDiscountPrice())
                    .text()
                    .replaceAll(ProductPriceFormatter.REGEX, StringUtils.EMPTY);
            if (StringUtils.isEmpty(price)) {
                String content = request.getProductPrice();
                price = element.attr(content);
            }
            return new BigDecimal(price).setScale(RESOLUTION_PRICE, RoundingMode.HALF_UP);
        }
    }

    private String formatPrice(final BuildProductObjectRequest request, Element element) throws UnsuccessfulFormatException {
        return ProductFormatter.format(request, ProductProperty.PRICE, element);
    }

    @Override
    public WebsiteScraper<Element, StaticWebsiteElementScrapRequest> getScraperService() {
        return (WebsiteScraper<Element, StaticWebsiteElementScrapRequest>) websiteScraperFactory.getElementScraper(false);
    }
}