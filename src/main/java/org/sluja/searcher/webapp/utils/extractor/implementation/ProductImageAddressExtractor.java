package org.sluja.searcher.webapp.utils.extractor.implementation;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Element;
import org.sluja.searcher.webapp.dto.product.ProductDTO;
import org.sluja.searcher.webapp.dto.product.request.search.object.BuildProductObjectRequest;
import org.sluja.searcher.webapp.dto.scraper.stat.StaticWebsiteElementScrapRequest;
import org.sluja.searcher.webapp.exception.scraper.ScraperIncorrectFieldException;
import org.sluja.searcher.webapp.service.factory.scraper.WebsiteScraperFactory;
import org.sluja.searcher.webapp.service.interfaces.scrap.IGetScraper;
import org.sluja.searcher.webapp.service.scraper.interfaces.WebsiteScraper;
import org.sluja.searcher.webapp.utils.extractor.Extractor;
import org.sluja.searcher.webapp.utils.logger.LoggerMessageUtils;
import org.sluja.searcher.webapp.utils.logger.LoggerUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
@Qualifier("productImageAddressExtractor")
@RequiredArgsConstructor
@Slf4j
public class ProductImageAddressExtractor implements Extractor<List<String>, Element, BuildProductObjectRequest>, IGetScraper {

    private final WebsiteScraperFactory websiteScraperFactory;
    private final LoggerMessageUtils loggerMessageUtils;
    @Override
    public List<String> extract(final Element element, final BuildProductObjectRequest request) {

        final List<Element> elements = request.getProductImageAddresses()
                .stream()
                .filter(StringUtils::isNotEmpty)
                .map(address -> {
                    final StaticWebsiteElementScrapRequest scrapRequest = new StaticWebsiteElementScrapRequest(address, element);
                    try {
                        return getScraperService().scrap(scrapRequest);
                    } catch (ScraperIncorrectFieldException e) {
                        log.error(loggerMessageUtils.getErrorLogMessage(LoggerUtils.getCurrentClassName(),
                                LoggerUtils.getCurrentMethodName(),
                                e.getMessageCode(),
                                e.getErrorCode()));
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

    @Override
    public WebsiteScraper<Element, StaticWebsiteElementScrapRequest> getScraperService() {
        return (WebsiteScraper<Element, StaticWebsiteElementScrapRequest>) websiteScraperFactory.getElementScraper(false);
    }
}
