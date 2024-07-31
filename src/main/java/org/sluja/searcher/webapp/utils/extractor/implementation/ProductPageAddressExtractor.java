package org.sluja.searcher.webapp.utils.extractor.implementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import org.sluja.searcher.webapp.utils.logger.LoggerMessageUtils;
import org.sluja.searcher.webapp.utils.logger.LoggerUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
@Qualifier("productPageAddressExtractor")
@RequiredArgsConstructor
@Slf4j
public class ProductPageAddressExtractor implements Extractor<List<String>, Element, BuildProductObjectRequest>, IGetScraper {

    private final WebsiteScraperFactory websiteScraperFactory;
    private final LoggerMessageUtils loggerMessageUtils;

    @Override
    public List<String> extract(final Element element, final BuildProductObjectRequest request) {
        final List<Element> elements = request.getProductPageAddresses()
                .stream()
                .filter(StringUtils::isNotEmpty)
                .map(address -> {
                    final StaticWebsiteElementScrapRequest scrapRequest = new StaticWebsiteElementScrapRequest(address, element);
                    try {
                        return getScraperService().scrap(scrapRequest);
                    } catch (final ScraperIncorrectFieldException e) {
                        log.error(loggerMessageUtils.getErrorLogMessage(LoggerUtils.getCurrentClassName(),
                                LoggerUtils.getCurrentMethodName(),
                                e.getMessageCode(),
                                e.getErrorCode()));
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
                        log.error(loggerMessageUtils.getErrorLogMessage(LoggerUtils.getCurrentClassName(),
                                LoggerUtils.getCurrentMethodName(),
                                e.getMessageCode(),
                                e.getErrorCode()));
                        return StringUtils.EMPTY;
                    }
                })
                .filter(StringUtils::isNotEmpty)
                .toList();
    }

    @Override
    public WebsiteScraper<Element, StaticWebsiteElementScrapRequest> getScraperService() {
        return (WebsiteScraper<Element, StaticWebsiteElementScrapRequest>) websiteScraperFactory.getElementScraper(false);
    }
}
