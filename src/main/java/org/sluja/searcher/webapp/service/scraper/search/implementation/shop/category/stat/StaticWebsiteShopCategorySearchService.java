package org.sluja.searcher.webapp.service.scraper.search.implementation.shop.category.stat;

import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.sluja.searcher.webapp.annotation.validation.InputValidation;
import org.sluja.searcher.webapp.builder.request.connect.stat.StaticWebsiteConnectRequestBuilder;
import org.sluja.searcher.webapp.dto.connect.StaticWebsiteConnectRequest;
import org.sluja.searcher.webapp.dto.product.request.shop.category.ShopCategoryPageSearchRequest;
import org.sluja.searcher.webapp.dto.scraper.stat.StaticWebsiteScrapRequest;
import org.sluja.searcher.webapp.exception.connection.ConnectionTimeoutException;
import org.sluja.searcher.webapp.exception.product.general.ProductNotFoundException;
import org.sluja.searcher.webapp.exception.product.shop.ShopCategoriesPageAddressesNotFoundException;
import org.sluja.searcher.webapp.service.factory.scraper.WebsiteScraperFactory;
import org.sluja.searcher.webapp.service.scraper.interfaces.WebsiteScraper;
import org.sluja.searcher.webapp.service.scraper.search.implementation.shop.category.ShopCategorySearchService;
import org.sluja.searcher.webapp.utils.connector.IConnector;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
@Qualifier("staticWebsiteShopCategorySearchService")
public class StaticWebsiteShopCategorySearchService extends ShopCategorySearchService<StaticWebsiteScrapRequest> {

    private final IConnector<Document, StaticWebsiteConnectRequest> staticWebsiteConnector;
    private final WebsiteScraperFactory websiteScraperFactory;

    @Override
    @InputValidation(inputs = {ShopCategoryPageSearchRequest.class})
    public List<String> searchList(final ShopCategoryPageSearchRequest request) throws ShopCategoriesPageAddressesNotFoundException {
        try {
            final StaticWebsiteConnectRequest connectRequest = StaticWebsiteConnectRequestBuilder.build(request.getHomePageAddress());
            final StaticWebsiteScrapRequest scrapRequest = new StaticWebsiteScrapRequest(StringUtils.EMPTY,
                    staticWebsiteConnector.connectAndGetPage(connectRequest));
            final List<Element> elements = (List<Element>) super.search(request, scrapRequest);
            if (CollectionUtils.isEmpty(elements)) {
                throw new ShopCategoriesPageAddressesNotFoundException(request.getShopName());
            }
            final String pageAddressExtractAttribute = request.getPageAddressExtractAttribute();
            return elements.stream()
                    .map(element -> element.attr(pageAddressExtractAttribute))
                    .map(element -> element.replaceAll("\\s", StringUtils.EMPTY))
                    .distinct()
                    .toList();
        } catch (ConnectionTimeoutException | IOException | ProductNotFoundException e) {
            //TODO logging
            throw new ShopCategoriesPageAddressesNotFoundException(request.getShopName());
        }
    }

    @Override
    public WebsiteScraper<List<Element>, StaticWebsiteScrapRequest> getScraperService() {
        return (WebsiteScraper<List<Element>, StaticWebsiteScrapRequest>) websiteScraperFactory.getScraper(false);
    }
}
